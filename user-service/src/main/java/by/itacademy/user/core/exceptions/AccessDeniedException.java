package by.itacademy.user.core.exceptions;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException() {
        super("Данному токену авторизации запрещено выполнять запросы на данный адрес");
    }
}
