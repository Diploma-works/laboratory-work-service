package com.vko.labworkproducer.exception;

public class UsernameOrPasswordException extends RuntimeException {

    public UsernameOrPasswordException() {
        super("Неправильно введен логин или пароль");
    }

}