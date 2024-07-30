package com.intexsoft.bank.exception;

/**
 * Used for exception during connection
 */
public class ConnectionException extends Throwable{
    public ConnectionException(String msg) {
        super(msg);
    }
}
