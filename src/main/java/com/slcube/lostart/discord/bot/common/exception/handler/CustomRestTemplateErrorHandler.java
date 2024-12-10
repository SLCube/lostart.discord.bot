package com.slcube.lostart.discord.bot.common.exception.handler;

import com.slcube.lostart.discord.bot.common.exception.CustomError;
import com.slcube.lostart.discord.bot.common.exception.response.LostarkApiResponseCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class CustomRestTemplateErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(503))) {
            throw new CustomError(LostarkApiResponseCode.SERVICE_UNAVAILABLE);
        }

        DefaultResponseErrorHandler defaultResponseErrorHandler = new DefaultResponseErrorHandler();
        defaultResponseErrorHandler.handleError(response);
    }
}
