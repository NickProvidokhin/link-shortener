package ru.providokhin.exception;

public class NotFoundException extends LinkShortenerException {

    public NotFoundException(String message) {
        super(message);
    }
}
