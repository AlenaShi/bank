package com.intexsoft.bank.command.account;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.model.Account;
import com.intexsoft.bank.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Used for getting account. Use with account id
 */
public class GetAccountCommand implements Command {

    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for finding";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for account id";
        }
        Long idLong = Long.valueOf(id);
        try {
            Account account = AccountService.getAccount(idLong);
            if (account.getNumber() == null) {
                return "No such account";
            }
            return account.toString();
        } catch (SQLException e) {
            LOG.error("Exception during finding account.", e);
            return "Exception during finding account";
        }
    }
}
