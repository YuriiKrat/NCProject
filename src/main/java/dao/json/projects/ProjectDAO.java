package dao.json.projects;

import dao.json.AbstractJsonDAOImpl;
import entities.project.Project;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class ProjectDAO extends AbstractJsonDAOImpl<Project, Integer> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);

    private static final String FILE_NAME = "json/entities/projects/Projects.txt";
    private File file;

    public ProjectDAO() {
        super(FILE_NAME);
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
