package dao.json.users;

import dao.json.AbstractJsonDAOImpl;
import entities.users.Manager;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class ManagerDAO extends AbstractJsonDAOImpl<Manager, Integer> {

    private static final Logger logger = Logger.getLogger(ManagerDAO.class);

    private static final String FILE_NAME = "json/entities/users/Managers.txt";
    private File file;

    public ManagerDAO() {
        super(FILE_NAME);
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
