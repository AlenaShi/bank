package com.intexsoft.bank.command.account;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.model.Bank;
import com.intexsoft.bank.model.Client;
import com.intexsoft.bank.service.AccountService;
import com.intexsoft.bank.service.BankService;
import com.intexsoft.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Using for creating account. Need client name and bank name
 */
public class CreateAccountCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 3) {
            return "No parameters for creating, use format {client_name} {bank_name}";
        }
        String clientName = parts[1];
        String bankName = parts[2];
        Bank bank;
        try {
            bank = BankService.getBank(bankName);
        } catch (SQLException e) {
           return "No such bank";
        }
        if (bank.getId() == null) {
            return "Not valid bank";
        }
        Client client;
        try {
            client = ClientService.getClient(clientName);
        } catch (DaoException e) {
            return "No such client";
        }
        if (client.getId() == null) {
            return "Please create client first. Use create_client {client_name} {individual} {bank}";
        }
        try {
            AccountService.createAccount(client.getId(), bank.getId());
            return "Account was created";
        } catch (SQLException e) {
            LOG.error("Exception during creating account.", e);
            return "Exception during account creation";
        }
    }
}
