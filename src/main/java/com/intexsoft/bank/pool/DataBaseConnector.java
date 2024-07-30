package com.intexsoft.bank.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Connection to database
 */
public class DataBaseConnector {

    private final static String DATABASE = "database";
    private final static String DATABASE_URL = "db.url";
    private final static String DATABASE_USER = "db.user";
    private final static String DATABASE_PASSWORD = "db.password";
    static final Logger LOG = LogManager.getRootLogger();

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

        } catch (SQLException e) {
            LOG.error("Can't register driver. ", e);
        }
    }

    /**
     * Used to create connection
     *
     * @return ProxyConnection object
     */
    static ProxyConnection getConnection() {
        ProxyConnection connection = null;
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE, Locale.getDefault());
        String url = resource.getString(DATABASE_URL);
        String user = resource.getString(DATABASE_USER);
        String pass = resource.getString(DATABASE_PASSWORD);
        try {
            connection = new ProxyConnection(DriverManager.getConnection(url, user, pass));
        } catch (SQLException e) {
            LOG.error("Can't create connection ", e);
        }
        return connection;
    }
}

