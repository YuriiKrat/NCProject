package dao.json.projects;

import dao.json.AbstractJsonDAOImpl;
import entities.project.Task;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class TaskDAO extends AbstractJsonDAOImpl<Task, Integer> {

    private static final Logger logger = Logger.getLogger(TaskDAO.class);

    private static final String FILE_NAME = "json/entities/projects/Tasks.txt";
    private File file;

    public TaskDAO() {
        super(FILE_NAME);
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
