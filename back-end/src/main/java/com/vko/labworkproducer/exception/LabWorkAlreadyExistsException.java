package com.vko.labworkproducer.exception;

public class LabWorkAlreadyExistsException extends RuntimeException {

    public LabWorkAlreadyExistsException() {
        super("Лабораторная работа с таким названием уже существует");
    }
}
