package entities.users;

import entities.project.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Manager extends User {

    private List<Project> projects;

    public Manager() {
        setUserRole(UserRole.MANAGER.toString());
        projects = new ArrayList<>();
    }

    public Manager(Integer id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName, UserRole.MANAGER.toString());
        projects = new ArrayList<>();
    }

    public Manager(Integer id, String username, String password, String firstName, String lastName,
                   List<Project> projects) {
        super(id, username, password, firstName, lastName, UserRole.MANAGER.toString());
        this.projects = projects;
    }

    public Manager(Manager manager) {
        this(manager.getId(), manager.getUsername(), manager.getPassword(), manager.getFirstName(),
                manager.getLastName());

        if (manager.getProjects() != null) {
            projects = new ArrayList<>();

            for (Project project : manager.getProjects()) {
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

        Manager manager = (Manager) o;

        return getProjects() != null ? getProjects().equals(manager.getProjects()) : manager.getProjects() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getProjects() != null ? getProjects().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Manager{" +
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
