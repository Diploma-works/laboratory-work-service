package com.vko.labworkproducer.exception;

public class TopicUsedInLabWorkException extends RuntimeException {

    public TopicUsedInLabWorkException() {
        super("Тема используется в лабораторной работе");
    }

}
