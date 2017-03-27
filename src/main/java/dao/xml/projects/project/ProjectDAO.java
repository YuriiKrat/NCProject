package dao.xml.projects.project;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.project.Project;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.03.17.
 */
public class ProjectDAO extends XmlWriter<Projects> implements AbstractDAO<Project, Integer> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);

    private static final String fileName = "xml/entities/projects/Project.xml";
    private Projects projects;

    public ProjectDAO() throws JAXBException {
        super(fileName);
        projects = new Projects();
    }

    @Override
    public void insert(Project obj) {
        logger.info("Attempting to insert project with id = " + obj.getId());
        boolean unique = true;
        projects = unmarshall();
        if (projects == null) {
            projects = new Projects();
        }
        for (int i = 0; i < projects.getProjects().size(); i++) {
            if (obj.getId().equals(projects.getProjects().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            projects.getProjects().add(obj);
            marshall(projects);
        } else {
            logger.warn("Error while inserting project! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Project> findAll() {
        logger.info("Attempting to retrieve all projects from xml!");
        projects = unmarshall();
        return projects != null ? projects.getProjects() : Collections.emptyList();
    }

    @Override
    public void update(Project obj) {
        logger.info("Attempting to update project with id = " + obj.getId() + " in xml!");

        projects = unmarshall();

        if (projects != null) {

            int i = 0;
            for (Project project : projects.getProjects()) {
                if (obj.getId().equals(project.getId())) {
                    projects.getProjects().set(i, obj);
                    marshall(projects);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Project get(Integer key) {
        logger.info("Attempting to retrieve project with id = " + key + " from xml!");

        Project project = null;
        projects = unmarshall();

        if (projects != null) {
            for (Project p : projects.getProjects()) {
                if (p.getId().equals(key)){
                    project = p;
                    break;
                }
            }
        }
        return project;
    }

    @Override
    public Project delete(Project obj) {
        logger.info("Attempting to delete project with id = " + obj.getId());

        Project project = null;
        projects = unmarshall();

        if (projects != null) {
            for (Project p : projects.getProjects()) {
                if (obj.equals(p)) {
                    project = p;
                    projects.getProjects().remove(project);
                    marshall(projects);
                    break;
                }
            }
        }
        return project;
    }

    @Override
    protected Class<Projects> getEntityClass() {
        return Projects.class;
    }

}


