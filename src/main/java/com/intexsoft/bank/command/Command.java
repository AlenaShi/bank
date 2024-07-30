package com.intexsoft.bank.command;

/**
 * Used for commands
 */
public interface Command {
    /**
     * used to execute commands
     *
     * @param parts command's parameters as string array
     * @return string as result
     */
    String execute(String[] parts);
}
