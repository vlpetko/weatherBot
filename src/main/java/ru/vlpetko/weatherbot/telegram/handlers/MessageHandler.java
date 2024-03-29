package ru.vlpetko.weatherbot.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.vlpetko.weatherbot.constants.BotMessageEnum;
import ru.vlpetko.weatherbot.constants.ButtonNameEnum;
import ru.vlpetko.weatherbot.model.City;
import ru.vlpetko.weatherbot.model.Client;
import ru.vlpetko.weatherbot.model.WeatherData;
import ru.vlpetko.weatherbot.service.CityService;
import ru.vlpetko.weatherbot.service.ClientService;
import ru.vlpetko.weatherbot.service.GeoApifyClient;
import ru.vlpetko.weatherbot.service.OpenMeteoApiClient;
import ru.vlpetko.weatherbot.telegram.buttons.InlineKeyboardMaker;
import ru.vlpetko.weatherbot.telegram.buttons.ReplyKeyboardMaker;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.vlpetko.weatherbot.utils.WeatherUtils.convertCurrentWeatherToString;
import static ru.vlpetko.weatherbot.utils.WeatherUtils.convertForecastToString;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final ClientService clientService;
    private final OpenMeteoApiClient openMeteoApiClient;
    private final GeoApifyClient geoApifyClient;
    private final CityService cityService;

    public BotApiMethod<?> answerMessage(Message message) throws IOException {
        String chatId = message.getChatId().toString();
        log.info("ChatId is: {}", chatId);

        String inputText = message.getText();
        Client client = clientService.chekClient(message.getFrom().getId(), message.getFrom().getFirstName(),
                message.getFrom().getLastName(), message.getDate());

        if (inputText == null) {
            if (message.getLocation() == null) {
                throw new IllegalArgumentException();
            } else {

                client = clientService.setQueryAndLocation(client, message.getLocation().getLatitude(),
                        message.getLocation().getLongitude());
                return getMainMenuMessage(chatId);
            }
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_FORECAST_BUTTON.getButtonName())) {
            return getForecastMessage(client, client.getWeatherQueries().get(client.getWeatherQueries().size() - 1)
                    .getLocation().getLatitude(), client.getWeatherQueries().get(client.getWeatherQueries().size() - 1)
                    .getLocation().getLongitude());
        } else if (inputText.equals(ButtonNameEnum.GET_CURRENT_WEATHER_BUTTON.getButtonName())) {
            return getCurrentWeatherMessage(client, client.getWeatherQueries().get(client.getWeatherQueries().size() - 1)
                    .getLocation().getLatitude(), client.getWeatherQueries().get(client.getWeatherQueries().size() - 1)
                    .getLocation().getLongitude());
        } else if (inputText.equals(ButtonNameEnum.GET_CITY_BUTTON.getButtonName())) {
            return getCityNameMessage(chatId);
        } else if (!inputText.isBlank()) {
            Optional<List<City>> cityList = Optional.ofNullable(cityService.getCity(inputText));
            if (cityList.isEmpty()) {
                return getCityNotFoundMessage(chatId);
            } else if (cityList.get().size() == 0) {
                return getCityNotFoundMessage(chatId);
            } else if (cityList.get().size() == 1) {
                client = clientService.setQueryAndLocation(client, cityList.get().get(0).getLatitude(),
                        cityList.get().get(0).getLongitude());
                return getMainMenuMessage(chatId);
            } else if (cityList.get().size() <= 20) {
                return getManyCitiesMessage(chatId, cityList.get());
            } else {
                return getBadCityRequestMessage(chatId);
            }
        } else {
            System.out.println("Unknoun text in message");
        }
        return null;
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getLocationKeyboard());
        return sendMessage;
    }

    private SendMessage getMainMenuMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.MAIN_MENU_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getForecastMessage(Client clientWithQuery, double latitude, double longitude) {
        String timeZone = geoApifyClient.getTimeZone(latitude, longitude);
        if (!timeZone.isBlank()) {
            List<WeatherData> weatherDataList = openMeteoApiClient.getAndSaveForecast(latitude, longitude,
                    timeZone, clientWithQuery);
            SendMessage sendMessage = new SendMessage(clientWithQuery.getUserId().toString(),
                    convertForecastToString(weatherDataList));
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getLocationKeyboard());
            return sendMessage;
        } else {
            return new SendMessage(clientWithQuery.getUserId().toString(), "Не удалось определить ваш часовой пояс," +
                    " попробуйте позднее, сейчас сервис недоступен");
        }
    }

    private SendMessage getCurrentWeatherMessage(Client clientWithQuery, double latitude, double longitude) {
        String timeZone = geoApifyClient.getTimeZone(latitude, longitude);
        if (!timeZone.isBlank()) {
            List<WeatherData> weatherDataList = openMeteoApiClient.getAndSaveCurrentWeather(latitude, longitude,
                    timeZone, clientWithQuery);
            SendMessage sendMessage = new SendMessage(clientWithQuery.getUserId().toString(),
                    convertCurrentWeatherToString(weatherDataList));
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getLocationKeyboard());
            return sendMessage;
        } else {
            return new SendMessage(clientWithQuery.getUserId().toString(), "Не удалось определить ваш часовой пояс," +
                    " попробуйте позднее, сейчас сервис недоступен");
        }
    }

    private SendMessage getCityNameMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CITY_REQUEST_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getCityNotFoundMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.CITY_NOT_FOUND_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getBadCityRequestMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.BAD_CITY_REQUEST_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    private SendMessage getManyCitiesMessage(String chatId, List<City> cityList) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.BAD_CITY_REQUEST_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons("prefix", cityList));
        return sendMessage;
    }
}
