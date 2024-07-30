package com.intexsoft.bank.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Used for working with commands
 */
public class CommandFactory {
    public static final Logger LOG = LogManager.getRootLogger();

    /**
     * USed to get command from string value
     *
     * @param commandName command's name
     * @return optional value of command
     */
    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null) {
            return Optional.empty();
        }
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            LOG.error("No such type of command");
            return Optional.empty();
        }
    }
}
