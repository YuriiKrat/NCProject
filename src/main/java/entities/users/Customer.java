package entities.users;

import entities.project.Project;

import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Customer extends User {

    private List<Project> projects;

    public Customer() {
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
