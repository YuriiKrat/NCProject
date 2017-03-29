package dao.xml.users.employee;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.users.Employee;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.03.17.
 */
public class EmployeeDAO extends XmlWriter<Employees> implements AbstractDAO<Employee, Integer> {

    private static final Logger logger = Logger.getLogger(EmployeeDAO.class);

    private static final String fileName = "xml/entities/users/Employee.xml";
    private Employees employees;

    public EmployeeDAO() throws JAXBException {
        super(fileName);
        employees = new Employees();
    }

    @Override
    public void insert(Employee obj) {
        logger.info("Attempting to insert employee with id = " + obj.getId());
        boolean unique = true;
        employees = unmarshall();
        if (employees == null) {
            employees = new Employees();
        }
        for (int i = 0; i < employees.getEmployees().size(); i++) {
            if (obj.getId().equals(employees.getEmployees().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            employees.getEmployees().add(obj);
            marshall(employees);
        } else {
            logger.warn("Error while inserting employee! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Employee> findAll() {
        logger.info("Attempting to retrieve all employees from xml!");
        employees = unmarshall();
        return employees != null ? employees.getEmployees() : Collections.emptyList();
    }

    @Override
    public void update(Employee obj) {
        logger.info("Attempting to update employee with id = " + obj.getId() + " in xml!");

        employees = unmarshall();

        if (employees != null) {

            int i = 0;
            for (Employee employee : employees.getEmployees()) {
                if (obj.getId().equals(employee.getId())) {
                    employees.getEmployees().set(i, obj);
                    marshall(employees);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Employee get(Integer key) {
        logger.info("Attempting to retrieve employee with id = " + key + " from xml!");

        Employee employee = null;
        employees = unmarshall();

        if (employees != null) {
            for (Employee e : employees.getEmployees()) {
                if (e.getId().equals(key)){
                    employee = e;
                    break;
                }
            }
        }
        return employee;
    }

    @Override
    public void delete(Employee obj) {
        logger.info("Attempting to delete employee with id = " + obj.getId());

        employees = unmarshall();

        if (employees != null) {
            for (Employee employee : employees.getEmployees()) {
                if (obj.equals(employee)) {
                    employees.getEmployees().remove(employee);
                    marshall(employees);
                    break;
                }
            }
        }
    }

    @Override
    protected Class<Employees> getEntityClass() {
        return Employees.class;
    }
}



