package dao.db.users;

import dao.db.AbstractDAOImpl;
import entities.users.Customer;
import entities.users.User;
import entities.users.UserRole;
import org.apache.log4j.Logger;

import java.util.List;

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
    public List<User> findAll() {
        return null;
    }

    @Override
    public User get(Integer key) {
        User user = null;

        return null;
    }

    @Override
    public User delete(User obj) {
        return null;
    }

    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAO();
        User user = new User();
        user.setId(53);
        user.setUsername("user");
        user.setLastName("last");
        user.setFirstName("first");
        user.setPassword("password");
        user.setUserRole(UserRole.ADMIN.toString() + "12");
//        List<Project> list = new ArrayList<>();
//        user.setProjects(list);
//        adminDAO.update(user);
//        Object o = new User();
        Object o = new Customer();
        System.out.println((o.getClass()));
//        System.out.println(User.class.getName());
//        System.out.println(o.getClass().getName());
//        try {
//            Class clazz = Class.forName("entities.users.User");
//            Method[] methods = clazz.getDeclaredMethods();
//            for(Method method: methods) {
//                System.out.println(method.getName() + " " + getReturnType(method));
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


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
