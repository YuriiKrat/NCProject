package entities.project;

import entities.users.Qualification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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

    private Integer id;
    private String name;
    private Integer estimate;
    private Date startDate;
    private List<Task> influencedTasks;
    private List<Task> subtasks;
    private Qualification preferredQualification;
    private TaskStatus status;

    public Task() {
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

    public Qualification getPreferredQualification() {
        return preferredQualification;
    }

    public void setPreferredQualification(Qualification preferredQualification) {
        this.preferredQualification = preferredQualification;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
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
