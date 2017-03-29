package dao.serialization.users;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.users.Employee;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class EmployeeDAO extends AbstractSerializationDAOImpl<Employee, Integer> {

    private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

    private static final String fileName = "serialization/entities/users/Company.txt";

    public EmployeeDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Employee entity) {
        return entity.getId();
    }

    @Override
    protected Class<Employee> getEntityClass() {
        return Employee.class;
    }
}
