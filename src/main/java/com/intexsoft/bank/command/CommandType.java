package com.intexsoft.bank.command;

import com.intexsoft.bank.command.account.*;
import com.intexsoft.bank.command.bank.CreateBankCommand;
import com.intexsoft.bank.command.bank.DeleteBankCommand;
import com.intexsoft.bank.command.bank.GetBankCommand;
import com.intexsoft.bank.command.bank.UpdateBankCommand;
import com.intexsoft.bank.command.client.CreateClientCommand;
import com.intexsoft.bank.command.client.DeleteClientCommand;
import com.intexsoft.bank.command.client.GetClientCommand;
import com.intexsoft.bank.command.client.UpdateClientCommand;

/**
 * Used for operating with command. Contain all command types.
 */
public enum CommandType {

    CREATE_BANK(new CreateBankCommand()), UPDATE_BANK(new UpdateBankCommand()), DELETE_BANK(new DeleteBankCommand()),
    GET_BANK(new GetBankCommand()),
    CREATE_CLIENT(new CreateClientCommand()), UPDATE_CLIENT(new UpdateClientCommand()),
    DELETE_CLIENT(new DeleteClientCommand()), GET_CLIENT(new GetClientCommand()),
    CREATE_ACCOUNT(new CreateAccountCommand()), GET_ACCOUNT(new GetAccountCommand()),
    DELETE_ACCOUNT(new DeleteAccountCommand()), UPDATE_ACCOUNT(new UpdateAccountCommand()),
    TRANSFER_MONEY(new TransferMoneyCommand()), CLOSE(new CloseCommand()), GET_CLIENT_ACCOUNT(new GetClientAccountCommand());
    private final Command command;

    private CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
