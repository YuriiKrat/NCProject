package dao.xml.users.dao;

import dao.xml.users.Users;
import entities.users.User;
import entities.users.UserRole;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */

public class UserDAO implements AbstractDAO<User, Integer>{

    private static final String fileName = "xml/entities/users/Users.xml";
    private File file;
    private JAXBContext jaxbContext;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;
    private Users users;

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    public UserDAO() throws JAXBException {
        users = new Users();
        jaxbContext = JAXBContext.newInstance(Users.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(fileName);

    }

    @Override
    public void insert(User obj) {
        try {
            if(file.exists()) {
                JAXBContext jaxbContext1 = JAXBContext.newInstance(Users.class);
                Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
                users = (Users) jaxbUnmarshaller1.unmarshal(file);
                System.out.println(users);
            }
            users.getUsers().add(obj);
            jaxbMarshaller.marshal(users, file);
        } catch (JAXBException e) {
            logger.warn("Can not insert value into xml-file! " + e.getMessage());
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

    public static void main(String[] args) throws JAXBException, ParserConfigurationException {
//        User manager = new User();
//        manager.setId(5);
//        manager.setFirstName("LKfj");
//        manager.setLastName("LALAL");
//        manager.setUsername("NELOH");
//        manager.setUserRole(UserRole.EMPLOYEE);
//        UserDAO managerDAO = new UserDAO();
//        managerDAO.insert(manager);
//        managerDAO.insert(manager);
//        managerDAO.insert(manager);
        unMarshalingExample();



    }

    private static void unMarshalingExample() throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //We had written this file in marshalling example
        Users emps = (Users) jaxbUnmarshaller.unmarshal( new File(fileName) );

        for(User emp : emps.getUsers())
        {
            System.out.println(emp.getId());
            System.out.println(emp.getFirstName());
        }
    }
}
