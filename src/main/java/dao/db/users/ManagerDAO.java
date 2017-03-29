package dao.db.users;

import dao.db.AbstractDAOImpl;
import dao.db.projects.ProjectDAO;
import entities.users.Manager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class ManagerDAO extends AbstractDAOImpl<Manager, Integer> {

    private static final Logger logger = Logger.getLogger(ManagerDAO.class);
    private ProjectDAO projectDAO;

    public ManagerDAO() {
        projectDAO = new ProjectDAO();
    }

    @Override
    public Manager get(Integer key) {
        Manager manager = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    manager = new Manager();
                    manager.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, manager, projectDAO);

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return manager;
    }

    @Override
    protected Class getEntityClass() {
        return Manager.class;
    }

    @Override
    protected Integer getEntityId(Manager entity) {
        return entity.getId();
    }
}
