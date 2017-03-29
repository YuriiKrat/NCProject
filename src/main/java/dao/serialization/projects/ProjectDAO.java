package dao.serialization.projects;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.project.Project;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class ProjectDAO extends AbstractSerializationDAOImpl<Project, Integer> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);

    private static final String fileName = "serialization/Project.txt";

    public ProjectDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Project entity) {
        return entity.getId();
    }

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }
}
