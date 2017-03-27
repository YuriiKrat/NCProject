package dao.xml.users.user;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.users.User;
import entities.users.UserRole;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */
public class UserDAO extends XmlWriter<Users> implements AbstractDAO<User, Integer> {

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private static final String fileName = "xml/entities/users/Users.xml";
    private Users users;

    public UserDAO() throws JAXBException {
        super(fileName);
        users = new Users();
    }

    @Override
    public void insert(User obj) {
        logger.info("Attempting to insert user with id = " + obj.getId());
        boolean unique = true;
        users = unmarshall();
        if (users == null) {
            users = new Users();
        }
        for (int i = 0; i < users.getUsers().size(); i++) {
            if (obj.getId().equals(users.getUsers().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            users.getUsers().add(obj);
            marshall(users);
        } else {
            logger.warn("Error while inserting user! User with id = " + obj.getId() + " already exists!");
        }

    }

    @Override
    public List<User> findAll() {
        logger.info("Attempting to retrieve all users from xml!");
        users = unmarshall();
        return users != null ? users.getUsers() : Collections.emptyList();
    }

    @Override
    public void update(User obj) {
        logger.info("Attempting to update user with id = " + obj.getId() + " in xml!");
        users = unmarshall();
        if (users != null) {
            for (int i = 0; i < users.getUsers().size(); i++) {
                if (obj.getId().equals(users.getUsers().get(i).getId())) {
                    users.getUsers().set(i, obj);
                    marshall(users);
                    break;
                }
            }
        }
    }

    @Override
    public User get(Integer key) {
        logger.info("Attempting to retrieve user with id = " + key + " from xml!");
        User user = null;
        users = unmarshall();
        if (users != null) {
            List<User> userList = users.getUsers().stream().filter(el -> el.getId().equals(key))
                    .collect(Collectors.toList());
            if (!userList.isEmpty()) {
                user = userList.get(0);
            }
        }
        return user;
    }

    @Override
    public User delete(User obj) {
        logger.info("Attempting to delete user with id = " + obj.getId());
        User user = null;
        users = unmarshall();
        if (users != null) {
            for (int i = 0; i < users.getUsers().size(); i++) {
                user = users.getUsers().get(i);
                if (obj.equals(user)) {
                    users.getUsers().remove(user);
                    marshall(users);
                    break;
                }
            }
        }
        return user;
    }

    public static void main(String[] args) throws JAXBException, ParserConfigurationException {
        User manager = new User();
        manager.setId(12);
        manager.setFirstName("FIRST");
        manager.setLastName("LastName");
        manager.setUsername("username");
        manager.setUserRole(UserRole.MANAGER.toString());

        UserDAO managerDAO = new UserDAO();

//        System.out.println(managerDAO.get(12));
//        System.out.println(manager.equals(managerDAO.get(12)));
//        managerDAO.update(manager);
        managerDAO.insert(manager);
//        managerDAO.insert(manager);
//        System.out.println(managerDAO.delete(manager));
//        managerDAO.insert(manager);
//        unMarshalingExample();
        List<User> userList = managerDAO.findAll();
        userList.forEach(el -> System.out.println(el.toString()));



    }

    @Override
    protected Class<Users> getEntityClass() {
        return Users.class;
    }
}
