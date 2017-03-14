package dao.collection.users;

import dao.xml.users.dao.AbstractDAO;
import entities.users.Customer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    public List<Customer> findAll() {
        return customers.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
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
    public Customer delete(Customer obj) {
        return customers.remove(obj.getId());
    }
}
