package entities.project;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Sprint {

    private Integer id;
    private String name;
    private Boolean isFinished;
    private List<Task> tasks;

    public Sprint() {
    }

    public Sprint(Integer id, String name, Boolean isFinished) {
        this.id = id;
        this.name = name;
        this.isFinished = isFinished;
    }

    public Sprint(Integer id, String name, Boolean isFinished, List<Task> tasks) {
        this(id, name, isFinished);
        this.tasks = tasks;
    }

    public Sprint(Sprint sprint) {
        this(sprint.getId(), sprint.getName(), sprint.isFinished);

        if (sprint.getTasks() != null) {
            tasks = new ArrayList<>();

            for (Task task : sprint.getTasks()) {
//                tasks.add(new Task(task));
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Boolean isFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
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

        Sprint sprint = (Sprint) o;

        if (getId() != null ? !getId().equals(sprint.getId()) : sprint.getId() != null) return false;
        if (getName() != null ? !getName().equals(sprint.getName()) : sprint.getName() != null) return false;
        if (isFinished != null ? !isFinished.equals(sprint.isFinished) : sprint.isFinished != null) return false;
        return getTasks() != null ? getTasks().equals(sprint.getTasks()) : sprint.getTasks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (isFinished != null ? isFinished.hashCode() : 0);
        result = 31 * result + (getTasks() != null ? getTasks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFinished=" + isFinished +
                ", tasks=" + tasks +
                '}';
    }
}
