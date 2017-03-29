package dao.serialization.projects;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.project.Task;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class TaskDAO extends AbstractSerializationDAOImpl<Task, Integer> {

    private static final Logger logger = Logger.getLogger(TaskDAO.class);

    private static final String fileName = "serialization/entities/projects/Company.txt";

    public TaskDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Task entity) {
        return entity.getId();
    }

    @Override
    protected Class<Task> getEntityClass() {
        return Task.class;
    }
}
