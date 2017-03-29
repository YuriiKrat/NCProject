package dao.db.connection;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class ConnectionManager {

    private final String HOST;
    private final String USERNAME;
    private final String PASSWORD;

    private static final String propFileName = "postgre.properties";
    private static final Logger logger = Logger.getLogger(ConnectionManager.class);

    private Connection connection;

    public ConnectionManager(){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Error, class driver is missing!" + e.getMessage());
        }

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Config failed!" + e.getMessage());
        }

        HOST = properties.getProperty("host");
        USERNAME = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");

    }

    public Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
                logger.debug("Connection established!");
            } catch (SQLException e) {
                logger.error("Connect failed!" + e.getMessage());
            }

        }
        return connection;

    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to close connection! " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ConnectionManager cm = new ConnectionManager();
        cm.getConnection();
    }

}
