package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Bank;
import com.intexsoft.bank.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bank dao implementation
 */
public class BankDaoImpl implements BankDao {
    private static final String SQL_CREATE_BANK = "INSERT INTO bank(name, commission_individual, commission_legal, creation_date, modification_date) VALUES (?,?,?,?,?)";
    private static final String SQL_DELETE_BANK_BY_ID = "DELETE FROM bank WHERE id=?";
    private static final String SQL_UPDATE_BANK_NAME = "UPDATE bank SET commission_individual=?,commission_legal=?, modification_date=? WHERE name=?";
    private static final String NAME = "name";
    public static final String COMMISSION_INDIVIDUAL = "commission_individual";
    public static final String COMMISSION_LEGAL = "commission_legal";
    private static final String SQL_SELECT_BANK_BY_ID = "SELECT name, commission_individual, commission_legal, creation_date, modification_date FROM bank WHERE id=?";
    public static final String CREATION_DATE = "creation_date";
    public static final String MODIFICATION_DATE = "modification_date";
    private static final String SQL_SELECT_BANK_BY_NAME = "SELECT id, commission_individual, commission_legal, creation_date, modification_date FROM bank WHERE name=?";
    public static final String ID = "id";
    private static BankDaoImpl instance;
    private static final AtomicBoolean create = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock();

    private BankDaoImpl() {
    }

    public static BankDaoImpl getInstance() {
        if (!create.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new BankDaoImpl();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public void create(Bank object) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_BANK)) {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getCommissionIndividual());
            statement.setInt(3, object.getCommissionLegal());
            statement.setTimestamp(4, object.getCreationDate());
            statement.setTimestamp(5, object.getModificationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in creating bank", e);
        }

    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BANK_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete by id", e);
        }
    }

    @Override
    public void update(Bank bank) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BANK_NAME)) {
            statement.setInt(1, bank.getCommissionIndividual());
            statement.setInt(2, bank.getCommissionLegal());
            statement.setTimestamp(3, bank.getModificationDate());
            statement.setString(4, bank.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in update bank name", e);
        }
    }

    @Override
    public Bank get(Long id) throws DaoException {
        Bank bank = new Bank();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BANK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                bank.setId(id);
                bank.setName(resultSet.getString(NAME));
                bank.setCreationDate(resultSet.getTimestamp(CREATION_DATE, Calendar.getInstance()));
                bank.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE, Calendar.getInstance()));
                bank.setCommissionIndividual(resultSet.getInt(COMMISSION_INDIVIDUAL));
                bank.setCommissionLegal(resultSet.getInt(COMMISSION_LEGAL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entity by id", e);
        }
        return bank;
    }

    @Override
    public Bank get(String name) throws DaoException {
        Bank bank = new Bank();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BANK_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                bank.setId(resultSet.getLong(ID));
                bank.setName(name);
                bank.setCreationDate(resultSet.getTimestamp(CREATION_DATE, Calendar.getInstance()));
                bank.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE, Calendar.getInstance()));
                bank.setCommissionIndividual(resultSet.getInt(COMMISSION_INDIVIDUAL));
                bank.setCommissionLegal(resultSet.getInt(COMMISSION_LEGAL));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entity by id", e);
        }
        return bank;
    }
}
