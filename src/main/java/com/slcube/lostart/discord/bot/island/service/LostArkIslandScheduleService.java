package com.slcube.lostart.discord.bot.island.service;

import com.slcube.lostart.discord.bot.island.repository.LostArkCalendarRepository;
import com.slcube.lostart.discord.bot.island.repository.dto.LostArkCalendarDto;
import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.slcube.lostart.discord.bot.common.constant.CategoryType.TRAVEL_ISLAND;
import static com.slcube.lostart.discord.bot.common.constant.TravelIslandTimeType.ALL_TIME;

@RequiredArgsConstructor
@Service
public class LostArkIslandScheduleService {

    private final LostArkCalendarRepository repository;

    public List<TravelIslandDto> getIslandList(LocalDate now) {
        List<LostArkCalendarDto> lostArkCalendarList = repository.getLostArkCalendarList();
        if (now.getDayOfWeek() == DayOfWeek.SATURDAY
                || now.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return filterTravelIslandOfWeekend(lostArkCalendarList, now);
        } else {
            return filterTravelIslandOfWeekDays(lostArkCalendarList, now);
        }
    }

    private List<TravelIslandDto> filterTravelIslandOfWeekDays(List<LostArkCalendarDto> calendarList, LocalDate now) {
        return calendarList.stream()
                // 오늘 날짜의 모험 섬 추출
                .filter(calendar -> calendar.getCategoryName().equals(TRAVEL_ISLAND.getCategory()))
                .filter(calendar -> calendar.getStartTimes().stream()
                        .anyMatch(startTime -> startTime.toLocalDate().equals(now)))

                // 오늘 날짜의 이름과 보상 추출
                .map(calendar -> {
                    LostArkCalendarDto.RewardItem rewardItem = calendar.getRewardItems().get(0);
                    String islandName = calendar.getContentsName();

                    List<LostArkCalendarDto.RewardItem.Item> items = rewardItem.getItems();
                    String rewardName = items.stream()
                            .filter(item -> {
                                List<LocalDateTime> startTimes = item.getStartTimes();
                                return startTimes != null && startTimes.stream()
                                        .anyMatch(startTime -> startTime.toLocalDate().equals(now));
                            })
                            .findFirst()
                            .map(LostArkCalendarDto.RewardItem.Item::getName)
                            .orElseThrow();
                    return new TravelIslandDto(ALL_TIME, islandName, rewardName);
                })
                .toList();
    }
    public List<TravelIslandDto> filterTravelIslandOfWeekend(List<LostArkCalendarDto> calendarList, LocalDate now) {
        return calendarList.stream()
                // 오늘 날짜의 모험 섬 추출
                .filter(calendar -> calendar.getCategoryName().equals(TRAVEL_ISLAND.getCategory()))
                .filter(calendar -> calendar.getStartTimes().stream()
                        .anyMatch(startTime -> startTime.toLocalDate().equals(now)))
                .map(calendar -> {
                    LostArkCalendarDto.RewardItem rewardItem = calendar.getRewardItems().get(0);
                    String islandName = calendar.getContentsName();

                    List<LostArkCalendarDto.RewardItem.Item> items = rewardItem.getItems();
                    String rewardName = items.stream()
                            .filter(item -> {
                                List<LocalDateTime> startTimes = item.getStartTimes();
                                return startTimes != null && startTimes.stream()
                                        .anyMatch(startTime -> startTime.toLocalDate().equals(now));
                            })
                            .findFirst()
                            .map(LostArkCalendarDto.RewardItem.Item::getName)
                            .orElseThrow();

                    LocalDateTime localDateTime = items.stream().filter(item -> {
                                List<LocalDateTime> startTimes = item.getStartTimes();
                                return startTimes != null && startTimes.stream()
                                        .anyMatch(startTime -> startTime.toLocalDate().equals(now));
                            })
                            .findFirst()
                            .map(LostArkCalendarDto.RewardItem.Item::getStartTimes)
                            .orElseThrow()
                            .get(0);

                    return new TravelIslandDto(localDateTime, islandName, rewardName);
                })
                .sorted(Comparator.comparing(TravelIslandDto::getTimeType))
                .toList();
    }
}


