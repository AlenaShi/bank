package com.intexsoft.bank.command.client;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.exception.ServiceException;
import com.intexsoft.bank.model.Bank;
import com.intexsoft.bank.service.BankService;
import com.intexsoft.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for creating client. Need client name, boolean individual and bank name. Also create account.
 */
public class CreateClientCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 4) {
            return "No parameters for creating, use format: create_client {client_name} {individual} {bank}";
        }
        String clientName = parts[1];
        String individual = parts[2];
        String bankName = parts[3];
        Bank bank;
        try {
            bank = BankService.getBank(bankName);
        } catch (SQLException e) {
            LOG.error("Can't get bank", e);
            return "Can't get bank";
        }
        if (bank.getName() == null) {
            return "Not valid bank";
        }
        boolean individualBool;
        if ("true".equalsIgnoreCase(individual) || "false".equalsIgnoreCase(individual)) {
            individualBool = Boolean.parseBoolean(individual);
        } else {
            return "Not valid individual use true or false";
        }

        try {
            ClientService.createClient(clientName, individualBool, bank.getId());
            return "Client was created";
        } catch (SQLException e) {
            LOG.error("Exception during creating client.", e);
            return "Exception during client creation";
        } catch (ServiceException e) {
            LOG.error("Exception during creating client.", e);
            return e.getMessage();
        }
    }
}
