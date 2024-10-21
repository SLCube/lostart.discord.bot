package com.slcube.lostart.discord.bot.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TravelIslandRewardType {

    GOLD("골드"),
    SILLING("실링"),
    CARD("전설 ~ 고급 카드 팩 IV"),
    PIRATE_TOKEN("크림스네일의 동전");

    private String rewardType;
}
