package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Client;
import com.intexsoft.bank.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of client's dao
 */
public class ClientDaoImpl implements ClientDao {
    private static final String SQL_CREATE_CLIENT = "INSERT INTO client(name, individual) VALUES (?,?)";
    private static final String SQL_DELETE_CLIENT_BY_ID = "DELETE FROM client WHERE id=?";
    private static final String SQL_UPDATE_CLIENT_ID = "UPDATE client SET individual=? WHERE id=?";
    public static final String NAME = "name";
    private static final String SQL_SELECT_CLIENT_BY_ID = "SELECT name, individual FROM client WHERE id=?";
    public static final String INDIVIDUAL = "individual";
    private static final String SQL_SELECT_CLIENT_BY_NAME = "SELECT id, individual FROM client WHERE name=?";
    public static final String ID = "id";
    ;
    private static ClientDaoImpl instance;
    private static final AtomicBoolean create = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock();

    private ClientDaoImpl() {
    }

    public static ClientDaoImpl getInstance() {
        if (!create.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ClientDaoImpl();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public void create(Client object) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_CLIENT)) {
            statement.setString(1, object.getName());
            statement.setBoolean(2, object.getIndividual());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in creating client", e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete by id", e);
        }

    }

    @Override
    public void update(Client object) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT_ID)) {
            statement.setBoolean(1, object.getIndividual());
            statement.setLong(2, object.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in updating client", e);
        }
    }

    @Override
    public Client get(Long id) throws DaoException {
        Client client = new Client();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                client.setId(id);
                client.setName(resultSet.getString(NAME));
                client.setIndividual(resultSet.getBoolean(INDIVIDUAL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entity by id", e);
        }
        return client;
    }

    @Override
    public Client get(String clientName) throws DaoException {
        Client client = new Client();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_NAME)) {
            statement.setString(1, clientName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                client.setId(resultSet.getLong(ID));
                client.setName(clientName);
                client.setIndividual(resultSet.getBoolean(INDIVIDUAL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entity by name", e);
        }
        return client;
    }

}
