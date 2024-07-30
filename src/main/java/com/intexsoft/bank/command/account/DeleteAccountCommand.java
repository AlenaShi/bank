package com.intexsoft.bank.command.account;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/***
 * Used for deleting account. Need account id as parameter
 */
public class DeleteAccountCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for deleting";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for account id";
        }
        Long idInt = Long.valueOf(id);

        try {
            AccountService.deleteAccount(idInt);
            return "Account was deleted";
        } catch (SQLException e) {
            LOG.error("Exception during deleting", e);
            return "Exception during account deleting";
        }
    }
}
