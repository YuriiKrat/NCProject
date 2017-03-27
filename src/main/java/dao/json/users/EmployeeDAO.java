package dao.json.users;

import dao.json.AbstractJsonDAOImpl;
import entities.users.Employee;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class EmployeeDAO extends AbstractJsonDAOImpl<Employee, Integer> {

    private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

    private static final String FILE_NAME = "json/entities/users/Employees.txt";
    private File file;

    public EmployeeDAO() {
        super(FILE_NAME);
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
