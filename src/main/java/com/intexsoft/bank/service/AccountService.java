package com.intexsoft.bank.service;

import com.intexsoft.bank.dao.AccountDao;
import com.intexsoft.bank.dao.AccountDaoImpl;
import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.exception.ServiceException;
import com.intexsoft.bank.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * USed for work with account
 */
public class AccountService {
    private AccountService() {
    }

    /**
     * Used for creating account
     *
     * @param id   client's id
     * @param bank bank's id
     * @throws DaoException throws from dao layer
     */
    public static void createAccount(Long id, Long bank) throws DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        Account account = new Account();
        account.setNumber(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setClientId(id);
        account.setBankId(bank);
        account.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        account.setModificationDate(new Timestamp(new java.util.Date().getTime()));
        accountDao.create(account);
    }

    /**
     * Used for deleting account
     *
     * @param id account's id
     * @throws DaoException throws from dao layer
     */
    public static void deleteAccount(Long id) throws DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        accountDao.delete(id);
    }

    /**
     * Used to get account
     *
     * @param id account's id
     * @return account object
     * @throws DaoException throws from dao layer
     */
    public static Account getAccount(Long id) throws DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        return accountDao.get(id);
    }

    /**
     * Used for transfer money.
     *
     * @param numberFrom account number from take money
     * @param numberTo   account number to put money
     * @param amount     amount for transfer
     * @throws ServiceException if no such account numbers or balance less than amount
     * @throws DaoException     throws from dao layer
     */
    public static void transferMoney(String numberFrom, String numberTo, BigDecimal amount) throws ServiceException, DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        Account accountTo = accountDao.get(numberTo);
        Account accountFrom = accountDao.get(numberFrom);
        if (accountTo.getId() == null || accountFrom.getId() == null) {
            throw new ServiceException("Wrong accounts");
        }
        BigDecimal accountFromBalance = accountFrom.getBalance();
        if (accountFromBalance.compareTo(amount) < 0) {
            throw new ServiceException("No such money to transfer");
        }
        BigDecimal newBalanceFrom = accountFromBalance.subtract(amount);
        accountFrom.setBalance(newBalanceFrom);
        accountDao.update(accountFrom);
        BigDecimal newBalanceTo = accountTo.getBalance().add(amount);
        accountTo.setBalance(newBalanceTo);
        try {
            accountDao.update(accountTo);
        } catch (SQLException e) {
            accountFrom.setBalance(accountFromBalance);
            accountDao.update(accountFrom);
        }
    }

    /**
     * Used for updating account
     *
     * @param id     account's id
     * @param amount to add to balance
     * @throws DaoException throws from dao layer
     */
    public static void updateAccount(String id, BigDecimal amount) throws DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        Long idAccount = Long.valueOf(id);
        Account account = accountDao.get(idAccount);
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(amount);
        account.setBalance(newBalance);
        accountDao.update(account);
    }

    public static List<Account> getClientAccount(Long clientId) throws DaoException {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        return accountDao.getClientAccount(clientId);
    }
}
