package dao.serialization.users;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.users.Customer;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class CustomerDAO extends AbstractSerializationDAOImpl<Customer, Integer> {

    private static final Logger logger = Logger.getLogger(CustomerDAO.class);

    private static final String fileName = "serialization/entities/users/Company.txt";

    public CustomerDAO() {
        super(fileName);
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
