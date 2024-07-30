package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Account;

import java.util.List;

/**
 * Used for working with account
 */
public interface AccountDao extends BasicDao<Account> {
    /**
     * Used for deleting client
     *
     * @param clientId client's id
     * @throws DaoException throw DaoException if catch SQLException
     */
    void deleteClientId(Long clientId) throws DaoException;

    /**
     * Used for getting account with number of account
     *
     * @param number number of account
     * @return account object
     * @throws DaoException if catch SQLException
     */
    Account get(String number) throws DaoException;

    List<Account> getClientAccount(Long clientId) throws DaoException;
}
