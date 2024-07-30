package com.intexsoft.bank.command.account;

import com.intexsoft.bank.exception.ServiceException;
import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Use to transfer money from one account to another. Use with account number from,
 * account number to and amount for transferring
 */
public class TransferMoneyCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        String numberFrom = parts[1];
        String numberTo = parts[2];
        String amount = parts[3];
        String regex = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
        if (!numberFrom.matches(regex) || !numberTo.matches(regex)) {
            return "Wrong account number";
        }
        if (numberFrom.equals(numberTo)) {
            return "Can't transfer between 1 account";
        }
        if (!amount.matches("[0-9]*")) {
            return "Wrong amount to transfer";
        }
        long aLong = Long.parseLong(amount);
        if (aLong <= 0) {
            return "Not valid amount, should be bigger than 0";
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(aLong);
        try {
            AccountService.transferMoney(numberFrom, numberTo, bigDecimal);
        } catch (ServiceException | SQLException e) {
            LOG.error("Couldn't transfer money", e);
            return "Couldn't transfer money, check balance";
        }
        return "Transfer was successful";
    }
}
