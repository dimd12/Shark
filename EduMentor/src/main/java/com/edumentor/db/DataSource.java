package com.edumentor.db;

import com.mysql.cj.jdbc.Driver;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A singleton class responsible for managing the database connection.
 * It loads database properties, establishes a connection, and provides access to the connection.
 *
 * @author adrian
 *
 */
public class DataSource {

    private DataSource() {
        loadProperties();
        testConnection();
    }

    /**
     * Returns the singleton instance of DataSource.
     *
     * @return The singleton instance of DataSource.
     */
    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    /**
     * Inner static class responsible for holding the singleton instance of DataSource.
     * This ensures thread-safe lazy initialization of the DataSource instance.
     */
    private static class DataSourceHolder {
        private static final DataSource INSTANCE = new DataSource();
    }

    private static final Logger LOG = Logger.getLogger(DataSource.class.getName());

    private String dbUrl;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private String dbDriverName;

    private Connection connection;

    /**
     * Returns a database connection. If no connection exists or the existing connection is closed,
     * a new connection is created using the database properties.
     *
     * @return A valid {@link Connection} object, or null if a connection could not be established.
     */
    public Connection getConnection() {
        try {
            if (connection == null || (connection != null && connection.isClosed())) {
                connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            }
            return connection;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, "Failed to get database connection", ex);
        }
        return null;
    }

    /**
     * Sets an existing {@link Connection} object to be used by this DataSource.
     *
     * @param connection The {@link Connection} object to set.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Loads database properties from a `jdbc.properties` file located in the classpath.
     * Properties include database URL, name, username, password, and driver name.
     */
    private void loadProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getResourceAsStream("/jdbc.properties");
            properties.load(inputStream);
            dbUrl = properties.getProperty("dbUrl");
            dbName = properties.getProperty("dbName");
            dbUsername = properties.getProperty("dbUsername");
            dbPassword = properties.getProperty("dbPassword");
            dbDriverName = properties.getProperty("dbDriverName");
            DriverManager.registerDriver((Driver) Class.forName(dbDriverName).newInstance());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Failed to load database properties", ex);
        }
    }

    /**
     * Tests the database connection by attempting to establish a connection
     * and logging a success message if successful.
     */
    public void testConnection() {
        if (getConnection() != null) {
            LOG.info("Successfully connected to the database!");
        }
    }
}
