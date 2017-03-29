package dao.collection.users;

import dao.collection.AbstractDAOImpl;
import entities.users.Manager;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.03.2017.
 */
public class ManagerDAO extends AbstractDAOImpl<Manager, Integer> {

    @Override
    public void insert(Manager obj) {
        entities.put(obj.getId(), new Manager(obj));
    }

    @Override
    public void update(Manager obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public void delete(Manager obj) {
        entities.remove(obj.getId());
    }

}
