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
        return filterTravelIsland(lostArkCalendarList);
    }

    private List<TravelIslandDto> filterTravelIsland(List<LostArkCalendarDto> calendarList) {
        return calendarList.stream()
                // 오늘 날짜의 모험 섬 추출
                .filter(calendar -> calendar.getCategoryName().equals(TRAVEL_ISLAND.getCategory()))
                .filter(calendar -> calendar.getStartTimes().stream()
                        .anyMatch(startTime -> startTime.toLocalDate().equals(LocalDate.now())))

                // 오늘 날짜의 이름과 보상 추출
                .map(calendar -> {
                    LostArkCalendarDto.RewardItem rewardItem = calendar.getRewardItems().get(0);
                    String islandName = calendar.getContentsName();

                    List<LostArkCalendarDto.RewardItem.Item> items = rewardItem.getItems();
                    String rewardName = items.stream()
                            .filter(item -> {
                                List<LocalDateTime> startTimes = item.getStartTimes();
                                return startTimes != null && startTimes.stream()
                                        .anyMatch(startTime -> startTime.toLocalDate().equals(LocalDate.now()));
                            })
                            .findFirst()
                            .map(LostArkCalendarDto.RewardItem.Item::getName)
                            .orElseThrow();
                    return new TravelIslandDto(islandName, rewardName);
                })
                .toList();
    }
}


