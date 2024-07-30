package com.intexsoft.bank.command.account;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Used for filling balance of the client's account
 */
public class UpdateAccountCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 3) {
            return "No parameters for updating, use format {id} {balance}";
        }
        String id = parts[1];
        String amount = parts[2];
        if (!amount.matches("[0-9]*") || !id.matches("[0-9]*")) {
            return "Wrong amount or id for filling account";
        }
        long aLong = Long.parseLong(amount);
        if (aLong <= 0) {
            return "Not valid amount, should be bigger than 0";
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(aLong);
        try {
            AccountService.updateAccount(id, bigDecimal);
            return "Account was updated";
        } catch (SQLException e) {
            LOG.error("Exception during account's updating", e);
            return "Exception during account's updating";
        }
    }
}
