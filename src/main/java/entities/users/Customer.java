package entities.users;

import entities.project.Project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer extends User {

    private List<Project> projects;

    public Customer() {
        setUserRole(UserRole.CUSTOMER.toString());
    }

    public Customer(Integer id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName, UserRole.CUSTOMER.toString());
    }

    public Customer(Integer id, String username, String password, String firstName, String lastName,
                    List<Project> projects) {
        super(id, username, password, firstName, lastName, UserRole.CUSTOMER.toString());
        this.projects = projects;
    }

    public Customer(Customer customer) {
        this(customer.getId(), customer.getUsername(), customer.getPassword(), customer.getFirstName(),
                customer.getLastName());

        if (customer.getProjects() != null) {
            projects = new ArrayList<>();

            for (Project project : customer.getProjects()) {
                projects.add(new Project(project));
            }
        }
    }


    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return getProjects() != null ? getProjects().equals(customer.getProjects()) : customer.getProjects() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getProjects() != null ? getProjects().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", userRole='" + getUserRole() + '\'' +
                ", projects=" + projects +
                '}';
    }
}
