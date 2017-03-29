package dao.json.companies;

import dao.json.AbstractJsonDAOImpl;
import entities.Company;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class CompanyDAO extends AbstractJsonDAOImpl<Company, Integer> {

    private static final Logger logger = Logger.getLogger(CompanyDAO.class);

    private static final String FILE_NAME = "json/entities/companies/Companies.txt";
    private File file;

    public CompanyDAO() {
        super(FILE_NAME);
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
