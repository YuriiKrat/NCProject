package dao.collection.users;

import dao.AbstractDAO;
import entities.users.Employee;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by win10 on 11.03.2017.
 */
public class EmployeeDAO implements AbstractDAO<Employee, Integer> {

    private Map<Integer, Employee> employees;

    public EmployeeDAO() {
        employees = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Employee obj) {
        employees.put(obj.getId(), obj);
    }

    @Override
    public Map<Integer, Employee> findAll() {
        return employees;
    }

    @Override
    public void update(Employee obj) {
        employees.put(obj.getId(), obj);
    }

    @Override
    public Employee get(Integer key) {
        return employees.get(key);
    }

    @Override
    public Employee delete(Employee obj) {
        return employees.remove(obj.getId());
    }
}
