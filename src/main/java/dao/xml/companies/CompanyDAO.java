package dao.xml.companies;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.Company;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */
public class CompanyDAO extends XmlWriter<Companies> implements AbstractDAO<Company, Integer> {

    private static final Logger logger = Logger.getLogger(CompanyDAO.class);

    private static final String fileName = "xml/entities/companies/Companies.xml";
    private Companies companies;

    public CompanyDAO() throws JAXBException {
        super(fileName);
        companies = new Companies();
    }

    @Override
    public void insert(Company obj) {
        logger.info("Attempting to insert company with id = " + obj.getId());
        boolean unique = true;
        companies = unmarshall();
        if (companies == null) {
            companies = new Companies();
        }
        for (int i = 0; i < companies.getCompanies().size(); i++) {
            if (obj.getId().equals(companies.getCompanies().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            companies.getCompanies().add(obj);
            marshall(companies);
        } else {
            logger.warn("Error while inserting user! Company with id = " + obj.getId() + " already exists!");
        }

    }

    @Override
    public List<Company> findAll() {
        logger.info("Attempting to retrieve all companies from xml!");
        companies = unmarshall();
        return companies != null ? companies.getCompanies() : Collections.emptyList();
    }

    @Override
    public void update(Company obj) {
        logger.info("Attempting to update company with id = " + obj.getId() + " in xml!");
        companies = unmarshall();
        if (companies != null) {
            for (int i = 0; i < companies.getCompanies().size(); i++) {
                if (obj.getId().equals(companies.getCompanies().get(i).getId())) {
                    companies.getCompanies().set(i, obj);
                    marshall(companies);
                    break;
                }
            }
        }
    }

    @Override
    public Company get(Integer key) {
        logger.info("Attempting to retrieve company with id = " + key + " from xml!");

        Company company = null;
        companies = unmarshall();

        if (companies != null) {
            for (Company p : companies.getCompanies()) {
                if (p.getId().equals(key)){
                    company = p;
                    break;
                }
            }
        }
        return company;
    }

    @Override
    public Company delete(Company obj) {
        logger.info("Attempting to delete company with id = " + obj.getId());

        Company company = null;
        companies = unmarshall();

        if (companies != null) {
            for (Company p : companies.getCompanies()) {
                if (obj.equals(p)) {
                    company = p;
                    companies.getCompanies().remove(company);
                    marshall(companies);
                    break;
                }
            }
        }
        return company;
    }

    @Override
    protected Class<Companies> getEntityClass() {
        return Companies.class;
    }

}
