package dao.json.users;

import dao.json.AbstractJsonDAOImpl;
import entities.users.Customer;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class CustomerDAO extends AbstractJsonDAOImpl<Customer, Integer> {

    private static final Logger logger = Logger.getLogger(CustomerDAO.class);

    private static final String FILE_NAME = "json/entities/users/Customers.txt";
    private File file;

    public CustomerDAO() {
        super(FILE_NAME);
    }

    @Override
    protected Integer getEntityId(Customer entity) {
        return entity.getId();
    }

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

}
