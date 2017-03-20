package dao.db.users;

import dao.AbstractDAO;
import dao.db.connection.ConnectionManager;
import entities.users.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class AdminDAO implements AbstractDAO<User, Integer> {

    private ConnectionManager connectionManager;
    private static final Logger logger = Logger.getLogger(AdminDAO.class);
    private static final String propFileName = "entities-types.properties";
    private Properties properties;

    public AdminDAO() {
        connectionManager = new ConnectionManager();
        properties = new Properties();
    }

    @Override
    public void insert(User obj) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Config failed!" + e.getMessage());
        }
//        String query = "INSERT INTO " + attributeType + " (name) VALUES (?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
//            statement = connection.prepareStatement(query);
            connection.setAutoCommit(false);
//            statement.setString(1, name);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
//            logger.warn("Can not execute query when insert into " + entitiesType + "!\n"
//                    + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.warn("Can not rollback transaction when insert administrator! " + e.getMessage());
            }
        } finally {
            if (statement != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.warn("Can not close statement when insert administrator! " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.warn("Can not close connection when insert administrator! " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public User get(Integer key) {
        return null;
    }

    @Override
    public User delete(User obj) {
        return null;
    }
}
