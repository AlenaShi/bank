package com.intexsoft.bank.command;

/**
 * Used for wrong or empty command
 */
public class EmptyCommand implements Command {
    @Override
    public String execute(String[] parts) {
        return "No such command. Use for " +
                "create bank: create_bank {name} {commission_individual} {commission_legal},\n " +
                "delete bank: delete_bank {id}, \n " +
                "get bank: get_bank {id},\n " +
                "update bank: update_bank {name} {commission_individual} {commission_legal},\n " +
                "create client: create_client {client_name} {individual} {bank},\n " +
                "delete client: delete_client {id},\n " +
                "get client: get_client {id},\n " +
                "update client: update_client {id} {individual},\n " +
                "create account: create_account {client_name} {bank_name},\n " +
                "delete account: delete_account {id},\n " +
                "get account: get_account {id},\n " +
                "fill account: update_account {id} {balance},\n " +
                "transfer money: transfer_money {accountNumberFrom} {accountNumberTo} {amount},\n " +
                "for closing use: close";
    }
}
