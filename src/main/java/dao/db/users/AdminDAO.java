package dao.db.users;

import dao.db.AbstractDAOImpl;
import entities.users.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class AdminDAO extends AbstractDAOImpl<User, Integer> {

    private static final Logger logger = Logger.getLogger(AdminDAO.class);

    public AdminDAO() {

    }

    @Override
    public User get(Integer key) {
        User user = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    user = new User();
                    user.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, user, null);

        } catch (SQLException e) {
            logger.error("Failed to retrieve user from db! " + e.getMessage());
        }
        return user;
    }

    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAO();
        System.out.println(adminDAO.get(68));
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    protected Integer getEntityId(User entity) {
        return entity.getId();
    }
}
