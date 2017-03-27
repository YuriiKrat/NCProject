package dao.collection.users;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.users.Customer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
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
    public Customer delete(Customer obj) {
        return entities.remove(obj.getId());
    }
}
