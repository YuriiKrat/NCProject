package dao.collection.project;

import dao.collection.AbstractDAOImpl;
import entities.project.Task;

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
    public void delete(Task obj) {
        entities.remove(obj.getId());
    }
}
