package com.slcube.lostart.discord.bot.island.service.dto;

import com.slcube.lostart.discord.bot.common.constant.TravelIslandTimeType;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.slcube.lostart.discord.bot.common.constant.TravelIslandTimeType.*;

@Getter
public class TravelIslandDto {
    private TravelIslandTimeType timeType;
    private String islandName;
    private String rewardType;

    public TravelIslandDto(TravelIslandTimeType timeType, String islandName, String rewardType) {
        this.timeType = timeType;
        this.islandName = islandName;
        this.rewardType = rewardType;
    }

    public TravelIslandDto(LocalDateTime startTime, String islandName, String rewardType) {
        this.timeType = startTime.getHour() < 14 ? MORNING : AFTERNOON;
        this.islandName = islandName;
        this.rewardType = rewardType;
    }
}
