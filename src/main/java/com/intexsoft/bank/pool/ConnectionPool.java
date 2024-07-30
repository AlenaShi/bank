package com.intexsoft.bank.pool;

import com.intexsoft.bank.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Working with connections
 */
public class ConnectionPool {

    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> usedConnections = new LinkedBlockingQueue<>();
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);
    private static final int DEFAULT_POOL_SIZE = 8;
    public static final Logger LOG = LogManager.getRootLogger();

    private ConnectionPool() {
        init();
    }

    private void init() {
        availableConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = DataBaseConnector.getConnection();
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                LOG.error("Can't add connection to pool. ", e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!create.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Used to take connection from pool
     *
     * @return ProxyConnection object
     */
    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            LOG.error("Can't take connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Used to get connection to pool
     *
     * @param connection
     * @throws ConnectionException
     */
    public void releaseConnection(Connection connection) throws ConnectionException {
        if (!(connection instanceof ProxyConnection)) {
            throw new ConnectionException("Used simple connection");
        }
        ProxyConnection proxyConnection = (ProxyConnection) connection;
        usedConnections.remove(connection);
        availableConnections.offer(proxyConnection);
    }

    /**
     * Used to close pool
     */
    public void closePool() {
        ProxyConnection connection = null;
        for (int i = 0; i < availableConnections.size(); i++) {
            try {
                connection = availableConnections.take();
            } catch (InterruptedException e) {
                LOG.error("Can't take connection. ", e);
            }
            try {
                connection.closeConnection();
            } catch (SQLException e) {
                LOG.error("Can't close connection. ", e);
            }
        }
    }

    /**
     * Used to deregister drivers
     */
    public void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOG.error(String.format("Error unregistering driver %s", driver), e);
            }
        }
    }
}
