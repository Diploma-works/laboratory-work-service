package com.vko.labworkproducer.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("Такой пользователь уже существует");
    }
}
