package com.slcube.lostart.discord.bot.island.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LostArkCalendarDto {

    @JsonProperty("CategoryName")
    private String categoryName;

    @JsonProperty("ContentsName")
    private String contentsName;

    @JsonProperty("ContentsIcon")
    private String contentsIcon;

    @JsonProperty("MinItemLevel")
    private String minItemLevel;

    @JsonProperty("StartTimes")
    private List<LocalDateTime> startTimes;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("RewardItems")
    private List<RewardItem> rewardItems;


    @Getter
    public static class RewardItem {
        @JsonProperty("ItemLevel")
        private String itemLevel;

        @JsonProperty("Items")
        private List<Item> items;

        @Getter
        public static class Item {
            @JsonProperty("Name")
            private String name;

            @JsonProperty("Icon")
            private String icon;

            @JsonProperty("Grade")
            private String grade;

            @JsonProperty("StartTimes")
            private List<LocalDateTime> startTimes;
        }
    }
}
