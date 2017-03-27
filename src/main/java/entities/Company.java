package entities;

import entities.project.Project;
import entities.users.Customer;
import entities.users.User;

import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Company {

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

    public Company(Integer id, String name, List<User> employees, List<Project> projects, List<Customer> customers) {
        this(id, name);
        this.employees = employees;
        this.projects = projects;
        this.customers = customers;
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
}
