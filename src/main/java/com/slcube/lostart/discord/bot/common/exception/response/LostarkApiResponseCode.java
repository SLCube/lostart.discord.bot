package com.slcube.lostart.discord.bot.common.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LostarkApiResponseCode {

    UN_AUTHORIZED("잘못된 인증키입니다. 관리자께 문의해주세요"),
    NOT_FOUND("Not Found"),
    INTERNAL_SERVER_ERROR("Internel Server Error"),
    SERVICE_UNAVAILABLE("로스트아크 서버 점검중입니다.");
    private final String statusCode;

}
