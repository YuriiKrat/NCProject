package dao.xml.users.customer;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.users.Customer;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Denys Vodotiiets.
 */
public class CustomerDAO extends XmlWriter<Customers> implements AbstractDAO<Customer, Integer> {

    private static final Logger logger = Logger.getLogger(CustomerDAO.class);

    private static final String fileName = "xml/entities/users/Customer.xml";
    private Customers customers;

    public CustomerDAO() throws JAXBException {
        super(fileName);
        customers = new Customers();
    }

    @Override
    public void insert(Customer obj) {
        logger.info("Attempting to insert customer with id = " + obj.getId());
        boolean unique = true;
        customers = unmarshall();
        if (customers == null) {
            customers = new Customers();
        }
        for (int i = 0; i < customers.getCustomers().size(); i++) {
            if (obj.getId().equals(customers.getCustomers().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            customers.getCustomers().add(obj);
            marshall(customers);
        } else {
            logger.warn("Error while inserting customer! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Customer> findAll() {
        logger.info("Attempting to retrieve all customers from xml!");
        customers = unmarshall();
        return customers != null ? customers.getCustomers() : Collections.emptyList();
    }

    @Override
    public void update(Customer obj) {
        logger.info("Attempting to update customer with id = " + obj.getId() + " in xml!");

        customers = unmarshall();

        if (customers != null) {

            int i = 0;
            for (Customer customer : customers.getCustomers()) {
                if (obj.getId().equals(customer.getId())) {
                    customers.getCustomers().set(i, obj);
                    marshall(customers);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Customer get(Integer key) {
        logger.info("Attempting to retrieve customer with id = " + key + " from xml!");

        Customer customer = null;
        customers = unmarshall();

        if (customers != null) {
            for (Customer c : customers.getCustomers()) {
                if (c.getId().equals(key)){
                    customer = c;
                    break;
                }
            }
        }
        return customer;
    }

    @Override
    public Customer delete(Customer obj) {
        logger.info("Attempting to delete customer with id = " + obj.getId());

        Customer customer = null;
        customers = unmarshall();

        if (customers != null) {
            for (Customer c : customers.getCustomers()) {
                if (obj.equals(c)) {
                    customer = c;
                    customers.getCustomers().remove(customer);
                    marshall(customers);
                    break;
                }
            }
        }
        return customer;
    }

    @Override
    protected Class<Customers> getEntityClass() {
        return Customers.class;
    }
}


