package com.intexsoft.bank.command.bank;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.model.Bank;
import com.intexsoft.bank.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for get bank by bank's id
 */
public class GetBankCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for finding";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for bank id";
        }
        Long idLong = Long.valueOf(id);
        try {
            Bank bank = BankService.getBank(idLong);
            if (bank.getName() == null) {
                return "No such bank";
            }
            return bank.toString();
        } catch (SQLException e) {
            LOG.error("Exception during finding bank.", e);
            return "Exception during finding bank";
        }
    }
}
