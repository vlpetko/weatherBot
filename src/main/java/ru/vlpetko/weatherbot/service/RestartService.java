package ru.vlpetko.weatherbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.vlpetko.weatherbot.telegram.buttons.ReplyKeyboardMaker;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class RestartService {

    @Value("${api-url}")
    String url;

    @Value("${bot-token}")
    String token;

    private final RestTemplate restTemplate;

    private final ReplyKeyboardMaker replyKeyboardMaker;

    public void setRestartKeyBoard(String chatId) {

        try {
            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("text", "Ваш прошлый запрос был не закончен! Начните снова");
            map.add("disable_notification", true);
            map.add("reply_markup", replyKeyboardMaker.getLocationKeyboard());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
            restTemplate.exchange(MessageFormat.format("{0}bot{1}/sendMessage?chat_id={2}", url, token, chatId),
                    HttpMethod.POST,
                    entity,
                    String.class);
        } catch (Exception e) {
            System.out.println("ups");
        }
    }
}
