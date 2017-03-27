package dao.collection.project;

import dao.collection.AbstractDAOImpl;
import entities.project.Project;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.03.2017.
 */
public class ProjectDAO extends AbstractDAOImpl<Project, Integer> {

    @Override
    public void insert(Project obj) {
        entities.put(obj.getId(), new Project(obj));
    }

    @Override
    public void update(Project obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public Project delete(Project obj) {
        return entities.remove(obj.getId());
    }
}