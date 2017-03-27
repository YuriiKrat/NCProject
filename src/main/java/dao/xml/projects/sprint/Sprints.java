package dao.xml.projects.sprint;

import entities.project.Sprint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denys Vodotiiets.
 */

@XmlRootElement(name = "sprints")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sprints {

    @XmlElement(name = "sprint")
    private List<Sprint> sprints;

    public Sprints() {
        sprints = new ArrayList<>();
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

}
