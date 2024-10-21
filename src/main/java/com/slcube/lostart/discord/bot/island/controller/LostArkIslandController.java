package com.slcube.lostart.discord.bot.island.controller;

import com.slcube.lostart.discord.bot.island.service.LostArkIslandScheduleService;
import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class LostArkIslandController {

    private final LostArkIslandScheduleService service;

    @GetMapping("/travelIsland")
    public List<TravelIslandDto> getLostArkIslandScheduleList() {
        return service.getIslandList();
    }
}
