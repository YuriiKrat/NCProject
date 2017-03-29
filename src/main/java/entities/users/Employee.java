package entities.users;

import entities.project.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Employee extends User {

    private String qualification;
    private List<Task> tasks;

    public Employee() {
        setUserRole(UserRole.EMPLOYEE.toString());
        tasks = new ArrayList<>();
    }

    public Employee(Integer id, String username, String password, String firstName, String lastName,
                    String qualification) {
        super(id, username, password, firstName, lastName, UserRole.EMPLOYEE.toString());
        this.tasks = new ArrayList<>();
        this.qualification = qualification;
    }

    public Employee(Integer id, String username, String password, String firstName, String lastName,
                    String qualification, List<Task> tasks) {
        this(id, username, password, firstName, lastName, qualification);
        this.tasks = tasks;
    }

    public Employee(Employee employee) {
        this(employee.getId(), employee.getUsername(), employee.getPassword(), employee.getFirstName(),
                employee.getLastName(), employee.getQualification());

        if (employee.getTasks() != null) {
            tasks = new ArrayList<>();

            for (Task task : employee.getTasks()) {
                tasks.add(new Task(task));
            }
        }
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Employee employee = (Employee) o;

        if (getQualification() != null ? !getQualification().equals(employee.getQualification()) : employee.getQualification() != null)
            return false;
        return getTasks() != null ? getTasks().equals(employee.getTasks()) : employee.getTasks() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getQualification() != null ? getQualification().hashCode() : 0);
        result = 31 * result + (getTasks() != null ? getTasks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", userRole='" + getUserRole() + '\'' +
                ", qualification='" + qualification + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
