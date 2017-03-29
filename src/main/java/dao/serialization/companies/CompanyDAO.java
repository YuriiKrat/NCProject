package dao.serialization.companies;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.Company;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class CompanyDAO extends AbstractSerializationDAOImpl<Company, Integer>{

    private static final Logger logger = Logger.getLogger(CompanyDAO.class);

    private static final String fileName = "serialization/entities/companies/Company.txt";

    public CompanyDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Company entity) {
        return entity.getId();
    }

    @Override
    protected Class<Company> getEntityClass() {
        return Company.class;
    }
}
