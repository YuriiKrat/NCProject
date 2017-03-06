package dao.collection;

import dao.AbstractDAO;
import entities.project.Task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    public Map<Integer, Task> findAll() {
        return tasks;
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
    public void delete(Task obj) {
        tasks.remove(obj.getId());
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<Integer, Task> tasks) {
        this.tasks = tasks;
    }
}
