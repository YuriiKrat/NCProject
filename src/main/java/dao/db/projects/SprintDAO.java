package dao.db.projects;

import dao.db.AbstractDAOImpl;
import entities.project.Sprint;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class SprintDAO extends AbstractDAOImpl<Sprint, Integer> {

    private static final Logger logger = Logger.getLogger(SprintDAO.class);
    private TaskDAO taskDAO;

    public SprintDAO() {
        taskDAO = new TaskDAO();
    }

    @Override
    public Sprint get(Integer key) {
        Sprint sprint = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    sprint = new Sprint();
                    sprint.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, sprint, taskDAO);

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return sprint;
    }

    @Override
    protected Class getEntityClass() {
        return Sprint.class;
    }

    @Override
    protected Integer getEntityId(Sprint entity) {
        return entity.getId();
    }
}
