package com.intexsoft.bank.command.client;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Use to update client. Need client's id and boolean value of "individual"
 */
public class UpdateClientCommand implements Command {
    public static final Logger LOG = LogManager.getRootLogger();

    @Override
    public String execute(String[] parts) {
        if (parts.length < 3) {
            return "No parameters for updating, use format: update_client {id} {individual}";
        }
        String id = parts[1];
        String individual = parts[2];
        if (!id.matches("[0-9]*")) {
            return "Use numbers for client id";
        }
        Long idInt = Long.valueOf(id);
        boolean individualBool;
        if ("true".equalsIgnoreCase(individual) || "false".equalsIgnoreCase(individual)) {
            individualBool = Boolean.parseBoolean(individual);
        } else {
            return "Not valid individual use true or false";
        }
        try {
            ClientService.updateClient(idInt, individualBool);
            return "Client was updated";
        } catch (SQLException e) {
            LOG.error("Exception during Bank's updating", e);
            return "Exception during Bank's updating";
        }
    }
}
