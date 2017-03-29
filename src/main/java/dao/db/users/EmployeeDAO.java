package dao.db.users;

import dao.db.AbstractDAOImpl;
import dao.db.projects.TaskDAO;
import entities.users.Employee;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class EmployeeDAO extends AbstractDAOImpl<Employee, Integer> {

    private static final Logger logger = Logger.getLogger(EmployeeDAO.class);
    private TaskDAO taskDAO;

    public EmployeeDAO() {
        taskDAO = new TaskDAO();
    }

    @Override
    public Employee get(Integer key) {
        Employee employee = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    employee = new Employee();
                    employee.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, employee, taskDAO);

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return employee;
    }

    @Override
    protected Class getEntityClass() {
        return Employee.class;
    }

    @Override
    protected Integer getEntityId(Employee entity) {
        return entity.getId();
    }
}
