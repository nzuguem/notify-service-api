package me.nzuguem.notify.exceptions;

public sealed abstract class NotifyException extends RuntimeException permits CustomerNotFoundException, ContextNoMatchTypeException{

    public NotifyException(String message) {
        super(message);
    }
}
