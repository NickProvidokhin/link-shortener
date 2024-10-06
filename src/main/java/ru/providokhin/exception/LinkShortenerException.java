package ru.providokhin.exception;

public class LinkShortenerException extends RuntimeException {
    LinkShortenerException(String message){
        super(message);
    }
    LinkShortenerException(String message, Exception exception){
        super(message, exception);
    }
}
