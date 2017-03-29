package dao.collection.users;

import dao.collection.AbstractDAOImpl;
import entities.users.Customer;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 11.03.2017.
 */
public class CustomerDAO extends AbstractDAOImpl<Customer, Integer> {

    @Override
    public void insert(Customer obj) {
        entities.put(obj.getId(), new Customer(obj));
    }

    @Override
    public void update(Customer obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public void delete(Customer obj) {
        entities.remove(obj.getId());
    }
}
