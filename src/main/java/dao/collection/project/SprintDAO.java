package dao.collection.project;

import dao.AbstractDAO;
import entities.project.Sprint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by win10 on 11.03.2017.
 */
public class SprintDAO implements AbstractDAO<Sprint, Integer> {

    private Map<Integer, Sprint> sprints;

    public SprintDAO() {
        sprints = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Sprint obj) {
        sprints.put(obj.getId(), obj);
    }

    @Override
    public Map<Integer, Sprint> findAll() {
        return sprints;
    }

    @Override
    public void update(Sprint obj) {
        sprints.put(obj.getId(), obj);
    }

    @Override
    public Sprint get(Integer key) {
        return sprints.get(key);
    }

    @Override
    public Sprint delete(Sprint obj) {
        return sprints.remove(obj.getId());
    }
}
