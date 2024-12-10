package com.slcube.lostart.discord.bot.common.config;

import com.slcube.lostart.discord.bot.common.exception.handler.CustomRestTemplateErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final CustomRestTemplateErrorHandler customRestTemplateErrorHandler;

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customRestTemplateErrorHandler);
        return restTemplate;
    }
}
