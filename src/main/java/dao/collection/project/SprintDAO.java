package dao.collection.project;

import dao.collection.AbstractDAOImpl;
import entities.project.Sprint;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.03.2017.
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
    public void delete(Sprint obj) {
        entities.remove(obj.getId());
    }
}