package dao.collection.users;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.users.Employee;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
 */
public class EmployeeDAO extends AbstractDAOImpl<Employee, Integer> {

    @Override
    public void insert(Employee obj) {
        entities.put(obj.getId(), new Employee(obj));
    }

    @Override
    public void update(Employee obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public Employee delete(Employee obj) {
        return entities.remove(obj.getId());
    }
}