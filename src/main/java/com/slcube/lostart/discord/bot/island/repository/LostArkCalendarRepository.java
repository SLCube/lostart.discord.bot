package com.slcube.lostart.discord.bot.island.repository;

import com.slcube.lostart.discord.bot.island.repository.dto.LostArkCalendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LostArkCalendarRepository {

    private final RestTemplate restTemplate;

    @Value("${lostark.api.key}")
    private String apiKey;

    @Value("${lostark.api.url}")
    private String apiUrl;

    public List<LostArkCalendarDto> getLostArkCalendarList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + apiKey);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<LostArkCalendarDto>> result = restTemplate.exchange(apiUrl, HttpMethod.GET, request, new ParameterizedTypeReference<>() {
        });
        return result.getBody();
    }
}
