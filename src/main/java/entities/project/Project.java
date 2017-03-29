package entities.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Project {

    private Integer id;
    private String name;
    private Date startDate;
    private Date finishDate;
    private List<Sprint> sprints;

    public Project() {
        sprints = new ArrayList<>();
    }

    public Project(Integer id, String name, Date startDate, Date finishDate) {
        this();
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Project(Integer id, String name, Date startDate, Date finishDate, List<Sprint> sprints) {
        this(id, name, startDate, finishDate);
        this.sprints = sprints;
    }

    public Project(Project project) {
        this(project.getId(), project.getName(), project.getFinishDate(), project.getFinishDate());

        if (project.getSprints() != null) {
            sprints = new ArrayList<>();

            for (Sprint sprint : project.getSprints()) {
                sprints.add(new Sprint(sprint));
            }
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
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

        Project project = (Project) o;

        if (getId() != null ? !getId().equals(project.getId()) : project.getId() != null) return false;
        if (getName() != null ? !getName().equals(project.getName()) : project.getName() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(project.getStartDate()) : project.getStartDate() != null)
            return false;
        if (getFinishDate() != null ? !getFinishDate().equals(project.getFinishDate()) : project.getFinishDate() != null)
            return false;
        return getSprints() != null ? getSprints().equals(project.getSprints()) : project.getSprints() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getFinishDate() != null ? getFinishDate().hashCode() : 0);
        result = 31 * result + (getSprints() != null ? getSprints().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", sprints=" + sprints +
                '}';
    }

}
