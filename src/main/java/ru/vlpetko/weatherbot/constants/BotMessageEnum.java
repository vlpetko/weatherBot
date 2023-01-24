package ru.vlpetko.weatherbot.constants;

public enum BotMessageEnum {

    //ответы на команды с клавиатуры

    EXCEPTION_ILLEGAL_MESSAGE("Нет, к такому меня не готовили! Я работаю или с текстом, или с файлом"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту"),
    HELP_MESSAGE("Вы можете узнать погоду по геолокации, либо указав город"),
    MAIN_MENU_MESSAGE("Вы можете узнать текущую погоду или прогноз на 7 дней."),
    CITY_NOT_FOUND_MESSAGE("Такой город не найден."),
    BAD_CITY_REQUEST_MESSAGE("Уточните ваш запрос, найдено больше одного города."),
    CITY_REQUEST_MESSAGE("Введите название города.");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
