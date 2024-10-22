package com.slcube.lostart.discord.bot.common.discord;

import com.slcube.lostart.discord.bot.island.listener.TravelIslandListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JdaConfig {

    private final TravelIslandListener travelIslandListener;

    @Value("${discord.bot.token}")
    private String discordBotToken;

    @Bean
    public JDA jda() {
        return JDABuilder.createDefault(discordBotToken)
                .setActivity(Activity.playing("모험 섬 알리미"))

                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(travelIslandListener)
                .build();
    }
}
