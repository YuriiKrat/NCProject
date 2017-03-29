package dao.xml.companies;

import entities.Company;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */
@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Companies {

    @XmlElement(name = "company")
    private List<Company> companies;

    public Companies() {
        companies = new ArrayList<>();
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
