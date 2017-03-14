package dao.collection.users;

import dao.xml.users.dao.AbstractDAO;
import entities.users.Manager;
import entities.users.UserRole;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
 */
public class ManagerDAO implements AbstractDAO<Manager, Integer> {

    private Map<Integer, Manager> managers;

    public ManagerDAO() {
        managers = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Manager obj) {
        Manager manager = obj.clone();
        managers.put(manager.getId(), manager);
    }

    @Override
    public List<Manager> findAll() {
        return managers.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
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

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setFirstName("ZHEKA");
        manager.setLastName("STEPANJUIK");
        manager.setUsername("LOH");
        manager.setUserRole(UserRole.EMPLOYEE);
        ManagerDAO managerDAO = new ManagerDAO();
        managerDAO.insert(manager);
        managerDAO.findAll().forEach(System.out::println);
        manager.setUsername("BARAN");
        System.out.println(managerDAO.get(1));
    }
}
