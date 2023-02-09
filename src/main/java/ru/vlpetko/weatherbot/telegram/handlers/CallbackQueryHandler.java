package ru.vlpetko.weatherbot.telegram.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.vlpetko.weatherbot.constants.BotMessageEnum;
import ru.vlpetko.weatherbot.model.City;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.service.CityService;
import ru.vlpetko.weatherbot.service.ClientService;
import ru.vlpetko.weatherbot.telegram.buttons.ReplyKeyboardMaker;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandler {

    private final CityService cityService;
    private final ClientService clientService;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) throws IOException {
        final String chatId = buttonQuery.getMessage().getChatId().toString();

        String data = buttonQuery.getData();
        Client client = clientService.getClient(buttonQuery.getMessage().getChatId());

        if (!data.isBlank()) {
            City city = cityService.getCityById(Long.valueOf(data));
            client = clientService.setQueryAndLocation(client, city.getLatitude(), city.getLongitude());
            return getMainMenuMessage(chatId);
        } else {
            return null;
        }

    }

    private SendMessage getMainMenuMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.MAIN_MENU_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }
}
