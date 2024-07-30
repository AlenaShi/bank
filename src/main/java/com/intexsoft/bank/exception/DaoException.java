package com.intexsoft.bank.exception;

import java.sql.SQLException;

/**
 * Used for covering SQLException with custom message
 */
public class DaoException extends SQLException {

    public DaoException(String msg, SQLException e) {
        super(msg, e);
    }
}


