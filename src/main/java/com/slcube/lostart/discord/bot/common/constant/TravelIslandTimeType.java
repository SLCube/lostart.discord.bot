package com.slcube.lostart.discord.bot.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TravelIslandTimeType {
    ALL_TIME("오전, 오후"),
    MORNING("오전"),
    AFTERNOON("오후");

    private final String timeType;
}
