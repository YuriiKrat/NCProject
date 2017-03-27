package dao.collection.project;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.project.Project;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
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