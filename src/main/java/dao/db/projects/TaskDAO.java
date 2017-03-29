package dao.db.projects;

import dao.db.AbstractDAOImpl;
import entities.project.Task;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class TaskDAO extends AbstractDAOImpl<Task, Integer> {

    private static final Logger logger = Logger.getLogger(TaskDAO.class);

    public TaskDAO() {

    }

    @Override
    public Task get(Integer key) {
        Task task = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    task = new Task();
                    task.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, task, this);
        } catch (SQLException e) {
            logger.error("Failed to retrieve user from db! " + e.getMessage());
        }
        return task;
    }

    @Override
    protected Class getEntityClass() {
        return Task.class;
    }

    @Override
    protected Integer getEntityId(Task entity) {
        return entity.getId();
    }
}
