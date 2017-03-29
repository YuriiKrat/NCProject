package dao.collection.users;

import dao.collection.AbstractDAOImpl;
import entities.users.Employee;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.03.2017.
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
    public void delete(Employee obj) {
        entities.remove(obj.getId());
    }
}