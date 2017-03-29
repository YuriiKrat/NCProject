package dao.collection.company;

import dao.collection.AbstractDAOImpl;
import entities.Company;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 28.03.17.
 */
public class CompanyDAO extends AbstractDAOImpl<Company, Integer> {

    @Override
    public void insert(Company obj) {
        entities.put(obj.getId(), new Company(obj));
    }

    @Override
    public void update(Company obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public void delete(Company obj) {
        entities.remove(obj.getId());
    }
}
