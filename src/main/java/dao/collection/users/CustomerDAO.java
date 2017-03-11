package dao.collection.users;

import dao.AbstractDAO;
import entities.users.Customer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by win10 on 11.03.2017.
 */
public class CustomerDAO implements AbstractDAO<Customer, Integer> {

    private Map<Integer, Customer> customers;

    public CustomerDAO() {
        customers = new ConcurrentHashMap<>();
    }

    @Override
    public void insert(Customer obj) {
        customers.put(obj.getId(), obj);
    }

    @Override
    public Map<Integer, Customer> findAll() {
        return customers;
    }

    @Override
    public void update(Customer obj) {
        customers.put(obj.getId(), obj);
    }

    @Override
    public Customer get(Integer key) {
        return customers.get(key);
    }

    @Override
    public void delete(Customer obj) {
        customers.remove(obj.getId());
    }
}
