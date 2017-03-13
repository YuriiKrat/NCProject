package dao.collection.project;

import dao.collection.CollectionAbstractDAO;
import entities.project.Project;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by win10 on 11.03.2017.
 */
public class ProjectDAO implements CollectionAbstractDAO<Project, Integer> {

    private Map<Integer, Project> projects;

    public ProjectDAO() {
        projects = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Project obj) {
        projects.put(obj.getId(), obj);
    }

    @Override
    public Map<Integer, Project> findAll() {
        return projects;
    }

    @Override
    public void update(Project obj) {
        projects.put(obj.getId(), obj);
    }

    @Override
    public Project get(Integer key) {
        return projects.get(key);
    }

    @Override
    public Project delete(Project obj) {
        return projects.remove(obj.getId());
    }
}
