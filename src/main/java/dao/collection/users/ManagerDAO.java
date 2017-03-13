package dao.collection.users;

import dao.collection.CollectionAbstractDAO;
import entities.users.Manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by win10 on 11.03.2017.
 */
public class ManagerDAO implements CollectionAbstractDAO<Manager, Integer> {

    private Map<Integer, Manager> managers;

    public ManagerDAO() {
        managers = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Manager obj) {
        managers.put(obj.getId(), obj);
    }

    @Override
    public Map<Integer, Manager> findAll() {
        return managers;
    }

    @Override
    public void update(Manager obj) {
        managers.put(obj.getId(), obj);
    }

    @Override
    public Manager get(Integer key) {
        return managers.get(key);
    }

    @Override
    public Manager delete(Manager obj) {
        return managers.remove(obj.getId());
    }
}
