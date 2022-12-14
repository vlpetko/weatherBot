package ru.vlpetko.weatherbot.constants;

public enum ButtonNameEnum {

    GET_CURRENT_WEATHER_BUTTON("Текущая погода"),

    GET_FORECAST_BUTTON("Прогноз погоды"),

    GET_LOCATION_BUTTON("Определить местоположение"),

    HELP_BUTTON("Помощь");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
