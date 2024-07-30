package com.intexsoft.bank.service;

import com.intexsoft.bank.dao.AccountDao;
import com.intexsoft.bank.dao.AccountDaoImpl;
import com.intexsoft.bank.dao.ClientDao;
import com.intexsoft.bank.dao.ClientDaoImpl;
import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.exception.ServiceException;
import com.intexsoft.bank.model.Account;
import com.intexsoft.bank.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Used for working with client
 */
public class ClientService {
    public static final Logger LOG = LogManager.getRootLogger();

    private ClientService() {
    }

    /**
     * Used for creating client
     *
     * @param clientName client name
     * @param individual boolean value if individual or not
     * @param bankId     bank's identifier
     * @throws DaoException     throws from dao layer
     * @throws ServiceException if you couldn't create account and client
     */
    public static void createClient(String clientName, Boolean individual, Long bankId) throws ServiceException, DaoException {
        ClientDao clientDao = ClientDaoImpl.getInstance();
        Client client = new Client();
        try {
            client.setName(clientName);
            client.setIndividual(individual);
            clientDao.create(client);
            client = clientDao.get(clientName);
            AccountDao accountDao = AccountDaoImpl.getInstance();
            Account account = new Account();
            account.setClientId(client.getId());
            account.setBankId(bankId);
            account.setBalance(BigDecimal.ZERO);
            account.setNumber(UUID.randomUUID().toString());
            account.setCreationDate(new Timestamp(new java.util.Date().getTime()));
            account.setModificationDate(new Timestamp(new java.util.Date().getTime()));
            accountDao.create(account);
        } catch (SQLException e) {
            if (client.getId() != null) {
                clientDao.delete(client.getId());
            }
            LOG.error("Get exception during creating client or account, try to delete client if couldn't create account ", e);
            throw new ServiceException("Couldn't create account ", e);
        }
    }

    /**
     * Used for deleting client
     *
     * @param clientId client's id
     * @throws DaoException throws from dao layer
     */
    public static void deleteClient(Long clientId) throws DaoException {
        ClientDao clientDao = ClientDaoImpl.getInstance();
        AccountDao accountDao = AccountDaoImpl.getInstance();
        accountDao.deleteClientId(clientId);
        clientDao.delete(clientId);
    }

    /**
     * Used for getting client
     *
     * @param id client's id
     * @return client object
     * @throws DaoException throws from dao layer
     */
    public static Client getClient(Long id) throws DaoException {
        ClientDao clientDao = ClientDaoImpl.getInstance();
        return clientDao.get(id);
    }

    /**
     * Used for updating client
     *
     * @param id         client's id
     * @param individual boolean value if individual or not
     * @throws DaoException throws from dao layer
     */
    public static void updateClient(Long id, Boolean individual) throws DaoException {
        ClientDao clientDao = ClientDaoImpl.getInstance();
        Client client = new Client();
        client.setId(id);
        client.setIndividual(individual);
        clientDao.update(client);
    }

    /**
     * Used for getting client
     *
     * @param clientName client name
     * @return client object
     * @throws DaoException throws from dao layer
     */
    public static Client getClient(String clientName) throws DaoException {
        ClientDao clientDao = ClientDaoImpl.getInstance();
        return clientDao.get(clientName);
    }
}
