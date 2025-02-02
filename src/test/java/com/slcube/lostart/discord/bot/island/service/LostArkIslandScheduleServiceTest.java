package com.slcube.lostart.discord.bot.island.service;

import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LostArkIslandScheduleServiceTest {

    @Autowired
    private LostArkIslandScheduleService service;

    @Test
    void test() {
        List<TravelIslandDto> islandList = service.getIslandList(LocalDate.now());
        assertThat(islandList).isNotEmpty();
    }
}