package ru.vlpetko.weatherbot.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vlpetko.weatherbot.telegram.WeatherBot;

@RestController
@AllArgsConstructor
public class WebHookController {

    private final WeatherBot weatherBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return weatherBot.onWebhookUpdateReceived(update);
    }
}
