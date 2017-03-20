package dao.collection.project;

import dao.AbstractDAO;
import entities.project.Sprint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    public List<Sprint> findAll() {
        return sprints.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
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
