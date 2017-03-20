package dao.collection.project;

import dao.AbstractDAO;
import entities.project.Project;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
 */
public class ProjectDAO implements AbstractDAO<Project, Integer> {

    private Map<Integer, Project> projects;

    public ProjectDAO() {
        projects = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Project obj) {
        projects.put(obj.getId(), obj);
    }

    @Override
    public List<Project> findAll() {
        return projects.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
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
