package dao.db.users;

import dao.db.AbstractDAOImpl;
import dao.db.projects.ProjectDAO;
import entities.users.Customer;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class CustomerDAO extends AbstractDAOImpl<Customer, Integer> {

    private static final Logger logger = Logger.getLogger(CustomerDAO.class);
    private ProjectDAO projectDAO;

    public CustomerDAO() {
        projectDAO = new ProjectDAO();
    }

    @Override
    public Customer get(Integer key) {
        Customer customer = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    customer = new Customer();
                    customer.setId(key);
                }
                resultSet.beforeFirst();
            }
            setFields(resultSet, customer, projectDAO);

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return customer;
    }

    @Override
    protected Class getEntityClass() {
        return Customer.class;
    }

    @Override
    protected Integer getEntityId(Customer entity) {
        return entity.getId();
    }
}
