package com.intexsoft.bank.command;

import com.intexsoft.bank.exception.CloseException;
import com.intexsoft.bank.exception.ServiceException;
import com.intexsoft.bank.pool.ConnectionPool;

/**
 * Used to close application. Close connection pool.
 */
public class CloseCommand implements Command {

    @Override
    public String execute(String[] parts) {
        ConnectionPool.getInstance().closePool();
        throw new CloseException("Close application");
    }
}
