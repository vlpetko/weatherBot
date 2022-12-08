package ru.vlpetko.weatherbot.constants;

public enum BotMessageEnum {

    //ответы на команды с клавиатуры

    EXCEPTION_ILLEGAL_MESSAGE("Нет, к такому меня не готовили! Я работаю или с текстом, или с файлом"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту"),
    HELP_MESSAGE("Do you need help?");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
