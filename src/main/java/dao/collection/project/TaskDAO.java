package dao.collection.project;

import dao.AbstractDAO;
import entities.project.Task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 06.03.17.
 */
public class TaskDAO implements AbstractDAO<Task, Integer> {

    private Map<Integer, Task> tasks;

    public TaskDAO() {
        tasks = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Task obj) {
        tasks.put(obj.getId(), obj);
    }

    @Override
    public List<Task> findAll() {
        return tasks.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public void update(Task obj) {
        tasks.put(obj.getId(), obj);
    }

    @Override
    public Task get(Integer key) {
        return tasks.get(key);
    }

    @Override
    public Task delete(Task obj) {
        return tasks.remove(obj.getId());
    }

}
