package com.slcube.lostart.discord.bot.island.controller.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LostArkIslandResponseDto {

    private LocalDateTime startTime;
    private List<LostArkIslandInfoDto> islandInfoDtoList;

    static class LostArkIslandInfoDto {
        private String name;
        private String rewardType;
    }
}
