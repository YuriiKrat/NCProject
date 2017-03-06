package entities.project;

import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class Project {

    private Integer id;
    private Date startDate;
    private Date finishDate;
    private List<Sprint> sprints;

    public Project() {
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
}
