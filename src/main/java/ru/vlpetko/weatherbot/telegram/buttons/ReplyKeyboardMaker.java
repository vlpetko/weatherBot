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

        KeyboardButton keyboardButton = new KeyboardButton(ButtonNameEnum.GET_LOCATION_BUTTON.getButtonName());
        keyboardButton.setRequestLocation(true);
        KeyboardButton keyboardButton1 = new KeyboardButton(ButtonNameEnum.GET_FORECAST_BUTTON.getButtonName());
        keyboardButton1.setRequestLocation(true);

        row1.add(keyboardButton);
        row1.add(new KeyboardButton(ButtonNameEnum.GET_CURRENT_WEATHER_BUTTON.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(keyboardButton1);
        row2.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
