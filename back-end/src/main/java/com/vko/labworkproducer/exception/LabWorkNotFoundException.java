package com.vko.labworkproducer.exception;

public class LabWorkNotFoundException extends RuntimeException {

    public LabWorkNotFoundException() {
        super("Лабораторная работа не была найдена");
    }
}
