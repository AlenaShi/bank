package com.intexsoft.bank.command.bank;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for updating bank such fields as commission_individual, commission_legal
 */
public class UpdateBankCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 4) {
            return "No parameters for updating, use format {name} {commission_individual} {commission_legal}";
        }
        String name = parts[1];
        String commissionIndividual = parts[2];
        String commissionLegal = parts[3];
        if (!commissionLegal.matches("[0-9]*") || !commissionIndividual.matches("[0-9]*")) {
            return "Use numbers for commission";
        }
        Integer commissionIndividualInt = Integer.valueOf(commissionIndividual);
        Integer commissionLegalInt = Integer.valueOf(commissionLegal);
        try {
            BankService.updateBank(name, commissionIndividualInt, commissionLegalInt);
            return "Bank was updated";
        } catch (SQLException e) {
            LOG.error("Exception during Bank's updating", e);
            return "Exception during Bank's updating";
        }
    }
}
