package dao.xml.users.manager;

import dao.AbstractDAO;

import dao.xml.XmlWriter;
import entities.users.Manager;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Denys Vodotiiets.
 */
public class ManagerDAO extends XmlWriter<Managers> implements AbstractDAO<Manager, Integer> {

    private static final Logger logger = Logger.getLogger(ManagerDAO.class);

    private static final String fileName = "xml/entities/users/Manager.xml";
    private Managers managers;

    public ManagerDAO() throws JAXBException {
        super(fileName);
        managers = new Managers();
    }

    @Override
    public void insert(Manager obj) {
        logger.info("Attempting to insert manager with id = " + obj.getId());
        boolean unique = true;
        managers = unmarshall();
        if (managers == null) {
            managers = new Managers();
        }
        for (int i = 0; i < managers.getManagers().size(); i++) {
            if (obj.getId().equals(managers.getManagers().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            managers.getManagers().add(obj);
            marshall(managers);
        } else {
            logger.warn("Error while inserting manager! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Manager> findAll() {
        logger.info("Attempting to retrieve all managers from xml!");
        managers = unmarshall();
        return managers != null ? managers.getManagers() : Collections.emptyList();
    }

    @Override
    public void update(Manager obj) {
        logger.info("Attempting to update manager with id = " + obj.getId() + " in xml!");

        managers = unmarshall();

        if (managers != null) {

            int i = 0;
            for (Manager manager : managers.getManagers()) {
                if (obj.getId().equals(manager.getId())) {
                    managers.getManagers().set(i, obj);
                    marshall(managers);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Manager get(Integer key) {
        logger.info("Attempting to retrieve manager with id = " + key + " from xml!");

        Manager manager = null;
        managers = unmarshall();

        if (managers != null) {
            for (Manager m : managers.getManagers()) {
                if (m.getId().equals(key)){
                    manager = m;
                    break;
                }
            }
        }
        return manager;
    }

    @Override
    public Manager delete(Manager obj) {
        logger.info("Attempting to delete manager with id = " + obj.getId());

        Manager manager = null;
        managers = unmarshall();

        if (managers != null) {
            for (Manager m : managers.getManagers()) {
                if (obj.equals(m)) {
                    manager = m;
                    managers.getManagers().remove(manager);
                    marshall(managers);
                    break;
                }
            }
        }
        return manager;
    }

    @Override
    protected Class<Managers> getEntityClass() {
        return Managers.class;
    }
}
