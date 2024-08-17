package com.karimzai.tbanktask.Application.Exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String name, Object key) {
        super(String.format("{%s} (%s) не найден.", name, key));
    }
}
