package entities;

import entities.project.Project;
import entities.users.Customer;
import entities.users.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

    @XmlAttribute
    private Integer id;
    private String name;
    private List<User> employees;
    private List<Project> projects;
    private List<Customer> customers;

    public Company() {
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Integer id, String name, List<User> employees, List<Project> projects,
                   List<Customer> customers) {
        this(id, name);
        this.employees = employees;
        this.projects = projects;
        this.customers = customers;
    }

    public Company(Company company) {
        this(company.getId(), company.getName());

        if (company.getCustomers() != null) {
            customers = new ArrayList<>();

            for (Customer customer : company.getCustomers()) {
                customers.add(new Customer(customer));
            }
        }

        if (company.getEmployees() != null) {
            employees = new ArrayList<>();

            for (User employee : company.getEmployees()) {
                employees.add(new User(employee));
            }
        }

        if (company.getProjects() != null) {
            projects = new ArrayList<>();

            for (Project project : company.getProjects()) {
                projects.add(new Project(project));
            }
        }
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (getId() != null ? !getId().equals(company.getId()) : company.getId() != null) return false;
        if (getName() != null ? !getName().equals(company.getName()) : company.getName() != null) return false;
        if (getEmployees() != null ? !getEmployees().equals(company.getEmployees()) : company.getEmployees() != null)
            return false;
        if (getProjects() != null ? !getProjects().equals(company.getProjects()) : company.getProjects() != null)
            return false;
        return getCustomers() != null ? getCustomers().equals(company.getCustomers()) : company.getCustomers() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmployees() != null ? getEmployees().hashCode() : 0);
        result = 31 * result + (getProjects() != null ? getProjects().hashCode() : 0);
        result = 31 * result + (getCustomers() != null ? getCustomers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", projects=" + projects +
                ", customers=" + customers +
                '}';
    }
}
