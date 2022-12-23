package ru.vlpetko.weatherbot.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vlpetko.weatherbot.constants.BotMessageEnum;
import ru.vlpetko.weatherbot.constants.ButtonNameEnum;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.model.CurrentWeather;
import ru.vlpetko.weatherbot.model.WeatherData;
import ru.vlpetko.weatherbot.service.ClientService;
import ru.vlpetko.weatherbot.service.GeoApifyClient;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;
import ru.vlpetko.weatherbot.telegram.buttons.ReplyKeyboardMaker;

import java.io.IOException;
import java.util.List;

import static ru.vlpetko.weatherbot.utils.WeatherUtils.convertCurrentWeatherToString;
import static ru.vlpetko.weatherbot.utils.WeatherUtils.convertForecastToString;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final ClientService clientService;
    private final OpenMeteoApiClient openMeteoApiClient;
    private final GeoApifyClient geoApifyClient;

    public BotApiMethod<?> answerMessage(Message message) throws IOException {
        String chatId = message.getChatId().toString();
        log.info("ChatId is: {}", chatId);

        String inputText = message.getText();
        Client client = clientService.chekClient(message.getFrom().getId(), message.getFrom().getFirstName(),
                message.getFrom().getLastName(), message.getDate());

        if (inputText == null) {
            if (message.getLocation() == null) {
                throw new IllegalArgumentException();
//            } else if (inputText.equals(ButtonNameEnum.GET_LOCATION_BUTTON.getButtonName())){
//                return getLocationMessage(chatId, message.getLocation().getLatitude(),message.getLocation().getLongitude());
            } else {
                return getForecastMessage(chatId, message.getLocation().getLatitude(),message.getLocation().getLongitude(), client);
            }
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
        SendMessage sendMessage = new SendMessage(chatId, convertCurrentWeatherToString(currentWeather));
        return sendMessage;
    }
    private SendMessage getLocationMessage(String chatId, double latitude, double longitude) {
        CurrentWeather currentWeather = openMeteoApiClient.getAndSaveLocationData("latitude="
                + latitude + "&longitude=" + longitude);
        SendMessage sendMessage = new SendMessage(chatId, convertCurrentWeatherToString(currentWeather));
        return sendMessage;
    }
    private SendMessage getForecastMessage(String chatId, double latitude, double longitude, Client client) {
        List<WeatherData> weatherDataList = openMeteoApiClient.getAndSaveForecast(latitude, longitude,
                geoApifyClient.getTimeZone(latitude, longitude), client);
        SendMessage sendMessage = new SendMessage(chatId, convertForecastToString(weatherDataList));
        return sendMessage;
    }
}
