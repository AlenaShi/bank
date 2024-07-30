package com.intexsoft.bank.command.client;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use for deleting client. Need client's id. Also delete client's accounts
 */
public class DeleteClientCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length <= 1) {
            return "No parameters for deleting";
        }
        String id = parts[1];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for client id";
        }
        Long idInt = Long.valueOf(id);

        try {
            ClientService.deleteClient(idInt);
            return "Client was deleted";
        } catch (SQLException e) {
            LOG.error("Exception during deleting", e);
            return "Exception during client deleting";
        }
    }
}
