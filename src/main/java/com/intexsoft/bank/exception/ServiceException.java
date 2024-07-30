package com.intexsoft.bank.exception;

import java.sql.SQLException;

/**
 * Used for exception in service layer
 */
public class ServiceException extends Throwable {
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, SQLException e) {
        super(msg, e);
    }
}
