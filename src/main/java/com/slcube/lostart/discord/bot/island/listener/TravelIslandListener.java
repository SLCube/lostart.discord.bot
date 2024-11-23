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
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
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

        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        if (user.isBot()) {
            return;
        }

        String msg = message.getContentDisplay();
        if (msg.equals("!모험섬")) {
            String receiveMessage = getTravelIslandReceiveMessage();
            textChannel.sendMessage(receiveMessage).queue();
        }
    }


    @Override
    public void onReady(ReadyEvent event) {

        TextChannel channel = event.getJDA().getTextChannelById(targetChannelId);
        if (channel != null) {
            long initialDelay = calculateInitialDelay();
            long period = TimeUnit.DAYS.toMillis(1);

            event.getJDA().getGatewayPool().scheduleAtFixedRate(
                    () -> channel.sendMessage(getTravelIslandReceiveMessage()).queue(),
                    initialDelay,
                    period,
                    TimeUnit.MILLISECONDS);
        }
    }

    private long calculateInitialDelay() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime targetTime = now.withHour(10).withMinute(30).withSecond(0).withNano(0);
        if (now.isAfter(targetTime)) {
            targetTime = targetTime.plusDays(1); // 내일 오전 10시 30분
        }

        // 현재 시간과 목표 시간 사이의 차이를 밀리초 단위로 계산
        return ChronoUnit.MILLIS.between(now, targetTime);
    }

    @NotNull
    private String getTravelIslandReceiveMessage() {
        LocalDate today = LocalDate.now();
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
        log.info("receiveMessage : {}", receiveMessage);
        return receiveMessage;
    }
}
