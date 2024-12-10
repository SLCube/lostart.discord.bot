package com.slcube.lostart.discord.bot.common.exception;

import com.slcube.lostart.discord.bot.common.exception.response.LostarkApiResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomError extends RuntimeException{

    private final LostarkApiResponseCode apiResponseCode;
}
