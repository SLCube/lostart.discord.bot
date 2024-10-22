package com.slcube.lostart.discord.bot.island.listener;

import com.slcube.lostart.discord.bot.island.service.LostArkIslandScheduleService;
import com.slcube.lostart.discord.bot.island.service.dto.TravelIslandDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TravelIslandListener extends ListenerAdapter {

    private final LostArkIslandScheduleService service;

    private static final String TARGET_CHANNEL_ID = "1298213366988406869";

    @SneakyThrows
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getChannel().getId().equals(TARGET_CHANNEL_ID)) return;

        LocalDate today = LocalDate.now();
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        }

        String[] messageArray = message.getContentDisplay().split(" ");
        if (messageArray[0].equalsIgnoreCase("!bot")) {
            String[] messageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);

            for (String msg : messageArgs) {
                if (msg.equals("모험섬")) {
                    List<TravelIslandDto> travelIslandList = service.getIslandList(today);
                    String receiveMessage = "";
                    for (TravelIslandDto travelIsland : travelIslandList) {
                        receiveMessage = receiveMessage.concat(travelIsland.getIslandName()).concat(" : ").concat(travelIsland.getRewardType()).concat("\n");
                    }

                    textChannel.sendMessage(receiveMessage).queue();
                }
            }
        }
    }
}
