package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Client;

/**
 * For working with client dao
 */
public interface ClientDao extends BasicDao<Client> {
    /**
     * Used for getting Client by client's name
     *
     * @param clientName client's name
     * @return client's object
     * @throws DaoException if catch SQLException
     */
    Client get(String clientName) throws DaoException;

}
