package com.intexsoft.bank.command.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateAccountCommandTest {
    private final CreateAccountCommand createAccountCommand = new CreateAccountCommand();

    @Test
    void execute1() {
        String[] parts = new String[]{"test", "test"};
        String expected = "No parameters for creating, use format {client_name} {bank_name}";
        String actual = createAccountCommand.execute(parts);
        assertEquals(expected, actual);
    }

    @Test
    void execute2() {
        String[] parts = new String[]{"create_bank","test", "no_name"};
        String expected = "Not valid bank";
        String actual = createAccountCommand.execute(parts);
        assertEquals(expected, actual);
    }
}