package ru.vlpetko.weatherbot.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vlpetko.weatherbot.constants.BotMessageEnum;
import ru.vlpetko.weatherbot.constants.ButtonNameEnum;
import ru.vlpetko.weatherbot.model.CurrentWeather;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;
import ru.vlpetko.weatherbot.telegram.TelegramApiClient;
import ru.vlpetko.weatherbot.telegram.buttons.ReplyKeyboardMaker;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final TelegramApiClient telegramApiClient;
    private final OpenMeteoApiClient openMeteoApiClient;

    public BotApiMethod<?> answerMessage(Message message) throws IOException {
        String chatId = message.getChatId().toString();
        log.info("ChatId is: {}", chatId);

        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_CURRENT_WEATHER_BUTTON.getButtonName())) {
            return getDataMessage(chatId);
        } else {
            System.out.println("Fucking text");
        }
        return null;
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getDataMessage(String chatId) {
        CurrentWeather currentWeather = openMeteoApiClient.getAndSaveData();
        SendMessage sendMessage = new SendMessage(chatId, currentWeather.toString());
        return sendMessage;
    }
}
