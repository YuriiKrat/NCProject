package dao.json.users;

import dao.json.AbstractJsonDAOImpl;
import entities.users.User;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class UserDAO extends AbstractJsonDAOImpl<User, Integer> {

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private static final String FILE_NAME = "json/entities/users/Users.txt";
    private File file;

    public UserDAO() {
        super(FILE_NAME);
    }

    @Override
    protected Integer getEntityId(User entity) {
        return entity.getId();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

}
