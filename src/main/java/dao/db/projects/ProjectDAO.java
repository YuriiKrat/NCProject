package dao.db.projects;

import dao.db.AbstractDAOImpl;
import entities.project.Project;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class ProjectDAO extends AbstractDAOImpl<Project, Integer> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);
    private SprintDAO sprintDAO;

    public ProjectDAO() {
        sprintDAO = new SprintDAO();
    }

    @Override
    public Project get(Integer key) {
        Project project = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    project = new Project();
                    project.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, project, sprintDAO);

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return project;
    }

    @Override
    protected Class getEntityClass() {
        return Project.class;
    }

    @Override
    protected Integer getEntityId(Project entity) {
        return entity.getId();
    }
}
