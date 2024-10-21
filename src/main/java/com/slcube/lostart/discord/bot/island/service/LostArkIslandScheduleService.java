package com.slcube.lostart.discord.bot.island.service;

import com.slcube.lostart.discord.bot.island.repository.LostArkCalendarRepository;
import com.slcube.lostart.discord.bot.island.repository.dto.LostArkCalendarDto;
import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.slcube.lostart.discord.bot.common.constant.CategoryType.TRAVEL_ISLAND;

@RequiredArgsConstructor
@Service
public class LostArkIslandScheduleService {

    private final LostArkCalendarRepository repository;

    public List<TravelIslandDto> getIslandList() {
        List<LostArkCalendarDto> lostArkCalendarList = repository.getLostArkCalendarList();

        List<LostArkCalendarDto> todayTravelIslandList = filterTravelIsland(lostArkCalendarList);
        List<TravelIslandDto> travelIslandList = filterTravelIslandReward(todayTravelIslandList);

        return travelIslandList;
    }

    private List<LostArkCalendarDto> filterTravelIsland(List<LostArkCalendarDto> calendarList) {
        return calendarList.stream().filter(calendar -> calendar.getCategoryName().equals(TRAVEL_ISLAND.getCategory()))
                .filter(calendar -> calendar.getStartTimes().stream()
                        .anyMatch(startTime -> startTime.toLocalDate().equals(LocalDate.now())))
                .toList();
    }

    private List<TravelIslandDto> filterTravelIslandReward(List<LostArkCalendarDto> todayTravelIslandList) {
        return todayTravelIslandList.stream()
                .map(todayTravelIsland -> {
                    TravelIslandDto travelIsland = new TravelIslandDto();
                    travelIsland.setIslandName(todayTravelIsland.getContentsName());    // 모험 섬 이름 할당

                    LostArkCalendarDto.RewardItem rewardItem = todayTravelIsland.getRewardItems().get(0);
                    List<LostArkCalendarDto.RewardItem.Item> items = rewardItem.getItems();

                    // 모험 섬 보상 추출
                    items.stream()
                            .filter(item -> {
                                List<LocalDateTime> startTimes = item.getStartTimes();
                                return startTimes != null && startTimes.stream()
                                        .anyMatch(startTime -> startTime.toLocalDate().equals(LocalDate.now()));
                            })
                            .findFirst()
                            .ifPresent(item -> travelIsland.setRewardType(item.getName()));

                    return travelIsland;
                })
                .toList();
    }
}

