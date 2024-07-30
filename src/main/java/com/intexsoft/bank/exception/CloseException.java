package com.intexsoft.bank.exception;

public class CloseException extends RuntimeException{
    public CloseException(String closeApplication) {super(closeApplication);
    }
}
