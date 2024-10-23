package com.slcube.lostart.discord.bot.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TravelIslandTimeType {
    ALL_TIME("[평일]"),
    MORNING("[주말 오전]"),
    AFTERNOON("[주말 오후]");

    private final String timeType;
}
