package com.vko.labworkproducer.exception;

public class TopicAlreadyExistsException extends RuntimeException {

    public TopicAlreadyExistsException() {
        super("Тема с таким названием уже существует");
    }
}
