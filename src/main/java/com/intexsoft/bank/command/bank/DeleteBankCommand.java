package com.intexsoft.bank.command.bank;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for deleting bank. Use with bank id as parameter
 */
public class DeleteBankCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for deleting";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for bank id";
        }
        Long idInt = Long.valueOf(id);

        try {
            BankService.deleteBank(idInt);
            return "Bank was deleted";
        } catch (SQLException e) {
            LOG.error("Exception during deleting", e);
            return "Exception during Bank deleting";
        }
    }
}
