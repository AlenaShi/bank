package com.intexsoft.bank.command.bank;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for creating bank. Use with parameters bank name, commission for individuals, commission for legal person
 */
public class CreateBankCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 4) {
            return "No parameters for creating, use format {name} {commission_individual} {commission_legal}";
        }
        String name = parts[1];
        String commissionIndividual = parts[2];
        String commissionLegal = parts[3];
        if (!commissionLegal.matches("[1-9]*") || !commissionIndividual.matches("[1-9]*")) {
            return "Use numbers for commission";
        }
        Integer commissionIndividualInt = Integer.valueOf(commissionIndividual);
        Integer commissionLegalInt = Integer.valueOf(commissionLegal);
        try {
            BankService.createBank(name, commissionIndividualInt, commissionLegalInt);
            return "Bank was created";
        } catch (SQLException e) {
            LOG.error("Exception during creating bank.", e);
            return "Exception during Bank creation";
        }
    }
}
