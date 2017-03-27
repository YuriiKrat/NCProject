package dao.collection.project;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.project.Task;
import entities.users.Manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 06.03.17.
 */
public class TaskDAO extends AbstractDAOImpl<Task, Integer> {

    @Override
    public void insert(Task obj) {
        entities.put(obj.getId(), new Task(obj));
    }

    @Override
    public void update(Task obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public Task delete(Task obj) {
        return entities.remove(obj.getId());
    }
}
