package com.intexsoft.bank.command.client;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.model.Client;
import com.intexsoft.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use to get client by client's id
 */
public class GetClientCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for finding";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for client id";
        }
        Long idLong = Long.valueOf(id);
        try {
            Client client = ClientService.getClient(idLong);
            if (client.getName() == null) {
                return "No such client";
            }
            return client.toString();
        } catch (SQLException e) {
            LOG.error("Exception during finding client.", e);
            return "Exception during finding client";
        }
    }
}
