package entities.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    @XmlAttribute
    private Integer id;
    private String name;
    private Integer estimate;
    private Date startDate;
    private String preferredQualification;
    private String taskStatus;
    private List<Task> influencedTasks;
    private List<Task> subtasks;

    public Task() {
        subtasks = new ArrayList<>();
        influencedTasks = new ArrayList<>();
    }

    public Task(Integer id, String name, Integer estimate, Date startDate, String preferredQualification, String taskStatus) {
        this();
        this.id = id;
        this.name = name;
        this.estimate = estimate;
        this.startDate = startDate;
        this.preferredQualification = preferredQualification;
        this.taskStatus = taskStatus;
    }

    public Task(Integer id, String name, Integer estimate, Date startDate, List<Task> influencedTasks,
                List<Task> subtasks, String preferredQualification, String taskStatus) {
        this(id, name, estimate, startDate, preferredQualification, taskStatus);
        this.influencedTasks = influencedTasks;
        this.subtasks = subtasks;
    }

    public Task(Task task) {
        this(task.getId(), task.getName(), task.getEstimate(), task.getStartDate(), task.getPreferredQualification(),
                task.getTaskStatus());

        if (task.getInfluencedTasks() != null) {
            influencedTasks = new ArrayList<>();

            for (Task influencedTask : task.getInfluencedTasks()) {
                influencedTasks.add(new Task(influencedTask));
            }
        }

        if (task.getSubtasks() != null) {
            subtasks = new ArrayList<>();

            for (Task subtask : task.getSubtasks()) {
                subtasks.add(new Task(subtask));
            }
        }
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Task> getInfluencedTasks() {
        return influencedTasks;
    }

    public void setInfluencedTasks(List<Task> influencedTasks) {
        this.influencedTasks = influencedTasks;
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public String getPreferredQualification() {
        return preferredQualification;
    }

    public void setPreferredQualification(String preferredQualification) {
        this.preferredQualification = preferredQualification;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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

        Task task = (Task) o;

        if (getId() != null ? !getId().equals(task.getId()) : task.getId() != null) return false;
        if (getName() != null ? !getName().equals(task.getName()) : task.getName() != null) return false;
        if (getEstimate() != null ? !getEstimate().equals(task.getEstimate()) : task.getEstimate() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(task.getStartDate()) : task.getStartDate() != null)
            return false;
        if (getPreferredQualification() != null ? !getPreferredQualification().equals(task.getPreferredQualification()) : task.getPreferredQualification() != null)
            return false;
        if (getTaskStatus() != null ? !getTaskStatus().equals(task.getTaskStatus()) : task.getTaskStatus() != null) return false;
        if (getInfluencedTasks() != null ? !getInfluencedTasks().equals(task.getInfluencedTasks()) : task.getInfluencedTasks() != null)
            return false;
        return getSubtasks() != null ? getSubtasks().equals(task.getSubtasks()) : task.getSubtasks() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEstimate() != null ? getEstimate().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getPreferredQualification() != null ? getPreferredQualification().hashCode() : 0);
        result = 31 * result + (getTaskStatus() != null ? getTaskStatus().hashCode() : 0);
        result = 31 * result + (getInfluencedTasks() != null ? getInfluencedTasks().hashCode() : 0);
        result = 31 * result + (getSubtasks() != null ? getSubtasks().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Task{id=");
        stringBuilder.append(id).append(", name='").append(name).append("\', estimate=").append(estimate)
                .append(", startDate=").append(startDate).append(", preferredQualification='")
                .append(preferredQualification).append("\', taskStatus='").append(taskStatus)
                .append("\' influenced tasks [ ");

        for (Task task : influencedTasks) {
            stringBuilder.append(id).append(' ');
        }
        stringBuilder.append("], subtasks [ ");

        for (Task task : subtasks) {
            stringBuilder.append(id).append(' ');
        }

        stringBuilder.append("] }");

        return stringBuilder.toString();
    }

}
