package com.slcube.lostart.discord.bot.island.listener;

import com.slcube.lostart.discord.bot.island.service.LostArkIslandScheduleService;
import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class TravelIslandListener extends ListenerAdapter {

    private final LostArkIslandScheduleService service;

    @Value("${discord.channel.id}")
    private String targetChannelId;

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getChannel().getId().equals(targetChannelId)) return;

        LocalDate today = LocalDate.now();
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        }

        String msg = message.getContentDisplay();
        if (msg.equals("!모험섬")) {
            List<TravelIslandDto> travelIslandList = service.getIslandList(today);
            String receiveMessage = "";
            if (travelIslandList.isEmpty()) {
                receiveMessage += "모험섬 정보가 없습니다.";
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

                receiveMessage = receiveMessage.concat(formatter.format(today) + " ").concat(dayOfWeek + " ").concat("모험 섬 정보입니다.\n");

                for (TravelIslandDto travelIsland : travelIslandList) {
                    receiveMessage = receiveMessage.concat(travelIsland.getTimeType().getTimeType() + " ").concat(travelIsland.getIslandName()).concat(" : ").concat(travelIsland.getRewardType()).concat("\n");
                }
            }
            textChannel.sendMessage(receiveMessage).queue();
        }
    }
}
