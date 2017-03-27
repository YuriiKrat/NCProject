package dao.collection.project;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.project.Sprint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
 */
public class SprintDAO extends AbstractDAOImpl<Sprint, Integer> {

    @Override
    public void insert(Sprint obj) {
        entities.put(obj.getId(), new Sprint(obj));
    }

    @Override
    public void update(Sprint obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public Sprint delete(Sprint obj) {
        return entities.remove(obj.getId());
    }
}