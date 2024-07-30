package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Account;
import com.intexsoft.bank.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Account dao implementation
 */
public class AccountDaoImpl implements AccountDao {
    private static final String SQL_CREATE_ACCOUNT = "INSERT INTO account(number, balance, client_id, bank_id, currency, creation_date, modification_date) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM account WHERE id=?";
    private static final String SQL_UPDATE_ACCOUNT_ID = "UPDATE account SET balance=?, modification_date=? WHERE id=?";
    ;
    private static final String SQL_SELECT_ACCOUNT_BY_ID = "SELECT number, balance, client_id, bank_id, currency, creation_date, modification_date FROM account WHERE id=?";
    private static final String SQL_DELETE_ACCOUNT_BY_CLIENT_ID = "DELETE FROM account WHERE client_id=?";
    ;
    private static final String SQL_SELECT_ACCOUNT_BY_NUMBER = "SELECT id, balance, client_id, bank_id, currency, creation_date, modification_date FROM account WHERE number=?";
    private static final String SQL_SELECT_ACCOUNT_BY_CLIENT = "SELECT id, number, balance, bank_id, currency, creation_date, modification_date FROM account WHERE client_id=?";
    ;
    public static final String NUMBER = "number";
    public static final String ID = "id";
    public static final String CREATION_DATE = "creation_date";
    public static final String MODIFICATION_DATE = "modification_date";
    public static final String BALANCE = "balance";
    public static final String BANK_ID = "bank_id";
    public static final String CLIENT_ID = "client_id";
    public static final String CURRENCY = "currency";

    private static AccountDaoImpl instance;
    private static final AtomicBoolean create = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock();

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        if (!create.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new AccountDaoImpl();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public void create(Account object) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ACCOUNT)) {
            statement.setString(1, object.getNumber());
            statement.setBigDecimal(2, object.getBalance());
            statement.setLong(3, object.getClientId());
            statement.setLong(4, object.getBankId());
            statement.setString(5, object.getCurrency());
            statement.setTimestamp(6, object.getCreationDate());
            statement.setTimestamp(7, object.getModificationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in creating account", e);
        }


    }

    @Override
    public void delete(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete by id", e);
        }
    }

    @Override
    public void update(Account account) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_ID)) {
            statement.setBigDecimal(1, account.getBalance());
            statement.setTimestamp(2, account.getModificationDate());
            statement.setLong(3, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in update account", e);
        }

    }

    @Override
    public Account get(Long id) throws DaoException {
        Account account = new Account();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                account.setId(id);
                account.setNumber(resultSet.getString(NUMBER));
                account.setCreationDate(resultSet.getTimestamp(CREATION_DATE, Calendar.getInstance()));
                account.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE, Calendar.getInstance()));
                account.setBalance(resultSet.getBigDecimal(BALANCE));
                account.setBankId(resultSet.getLong(BANK_ID));
                account.setClientId(resultSet.getLong(CLIENT_ID));
                account.setCurrency(resultSet.getString(CURRENCY));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entity by id", e);
        }
        return account;
    }

    @Override
    public void deleteClientId(Long clientId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_CLIENT_ID)) {
            statement.setLong(1, clientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception in delete by client id", e);
        }
    }

    @Override
    public Account get(String number) throws DaoException {
        Account account = new Account();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NUMBER)) {
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                account.setId(resultSet.getLong(ID));
                account.setNumber(number);
                account.setCreationDate(resultSet.getTimestamp(CREATION_DATE, Calendar.getInstance()));
                account.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE, Calendar.getInstance()));
                account.setBalance(resultSet.getBigDecimal(BALANCE));
                account.setBankId(resultSet.getLong(BANK_ID));
                account.setClientId(resultSet.getLong(CLIENT_ID));
                account.setCurrency(resultSet.getString(CURRENCY));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find account by number", e);
        }
        return account;
    }

    @Override
    public List<Account> getClientAccount(Long clientId) throws DaoException {
        List<Account> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_CLIENT)) {
            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet != null && resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong(ID));
                account.setNumber(resultSet.getString(NUMBER));
                account.setCreationDate(resultSet.getTimestamp(CREATION_DATE, Calendar.getInstance()));
                account.setModificationDate(resultSet.getTimestamp(MODIFICATION_DATE, Calendar.getInstance()));
                account.setBalance(resultSet.getBigDecimal(BALANCE));
                account.setBankId(resultSet.getLong(BANK_ID));
                account.setClientId(clientId);
                account.setCurrency(resultSet.getString(CURRENCY));
                list.add(account);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find account by number", e);
        }
        return list;
    }
}
