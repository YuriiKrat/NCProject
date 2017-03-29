package dao.serialization.users;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.users.Manager;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class ManagerDAO extends AbstractSerializationDAOImpl<Manager, Integer> {

    private static final Logger logger = Logger.getLogger(ManagerDAO.class);

    private static final String fileName = "serialization/entities/users/Company.txt";

    public ManagerDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Manager entity) {
        return entity.getId();
    }

    @Override
    protected Class<Manager> getEntityClass() {
        return Manager.class;
    }
}
