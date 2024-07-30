package com.intexsoft.bank.command.account;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Account;
import com.intexsoft.bank.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Used for getting all clients account
 */
public class GetClientAccountCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for finding";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for client id";
        }
        Long idLong = Long.valueOf(id);
        try {
            List<Account> accounts = AccountService.getClientAccount(idLong);
            if (accounts.isEmpty()) {
                return "No accounts for this client";
            }
            StringBuilder result = new StringBuilder();
            for (Account account : accounts) {
                result.append(account).append("\n");
            }
            return result.toString();
        } catch (DaoException e) {
            LOG.error("Exception during finding accounts.", e);
            return "Exception during finding accounts";
        }
    }
}
