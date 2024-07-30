package com.intexsoft.bank;

import com.intexsoft.bank.command.Command;
import com.intexsoft.bank.command.CommandFactory;
import com.intexsoft.bank.command.EmptyCommand;
import com.intexsoft.bank.exception.CloseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Please enter command");
            while (true) {
                String[] parts = reader.readLine().split(" ");
                Optional<Command> commandOptional = CommandFactory.defineCommand(parts[0]);
                Command command = commandOptional.orElse(new EmptyCommand());
                System.out.println(command.execute(parts));
            }
        } catch (CloseException e) {
            System.out.println("Close application");
        }
    }
}