package entities.users;

import com.sun.org.apache.xpath.internal.SourceTree;
import entities.project.Project;

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
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public Manager clone() {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setPassword(password);
        manager.setUsername(username);
        manager.setUserRole(UserRole.MANAGER.toString());
        System.out.println(manager);
        return manager;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
