package by.itacademy.user.core.exceptions;

public class ValidationException extends IllegalArgumentException{

    public ValidationException() {
        super("Запрос невалиден. Повторите попытку запроса");
    }
}
