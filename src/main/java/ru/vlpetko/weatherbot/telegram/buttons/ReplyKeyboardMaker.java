package ru.vlpetko.weatherbot.telegram.buttons;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.vlpetko.weatherbot.constants.ButtonNameEnum;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton(ButtonNameEnum.GET_FORECAST_BUTTON.getButtonName());
        KeyboardButton keyboardButton1 = new KeyboardButton(ButtonNameEnum.GET_CURRENT_WEATHER_BUTTON.getButtonName());

        row1.add(keyboardButton);
        row1.add(keyboardButton1);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getLocationKeyboard() {
        KeyboardRow row1 = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton(ButtonNameEnum.GET_LOCATION_BUTTON.getButtonName());
        keyboardButton.setRequestLocation(true);
        KeyboardButton keyboardButton1 = new KeyboardButton(ButtonNameEnum.GET_CITY_BUTTON.getButtonName());

        row1.add(keyboardButton);
        row1.add(keyboardButton1);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
