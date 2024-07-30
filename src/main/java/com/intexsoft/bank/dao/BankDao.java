package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Bank;

/**
 * Used for working with bank object in database
 */
public interface BankDao extends BasicDao<Bank> {
    /**
     * Used to get bank object by bank's name
     *
     * @param name bank's name
     * @return bank object
     * @throws DaoException if catch SQLException
     */
    Bank get(String name) throws DaoException;
}
