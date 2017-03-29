package dao.xml.projects.sprint;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.project.Sprint;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.util.Collections;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.03.17.
 */
public class SprintDAO extends XmlWriter<Sprints> implements AbstractDAO<Sprint, Integer> {

    private static final Logger logger = Logger.getLogger(SprintDAO.class);

    private static final String fileName = "xml/entities/projects/Sprint.xml";
    private Sprints sprints;

    public SprintDAO() throws JAXBException {
        super(fileName);
        sprints = new Sprints();
    }

    @Override
    public void insert(Sprint obj) {
        logger.info("Attempting to insert sprint with id = " + obj.getId());
        boolean unique = true;
        sprints = unmarshall();
        if (sprints == null) {
            sprints = new Sprints();
        }
        for (int i = 0; i < sprints.getSprints().size(); i++) {
            if (obj.getId().equals(sprints.getSprints().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            sprints.getSprints().add(obj);
            marshall(sprints);
        } else {
            logger.warn("Error while inserting sprint! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Sprint> findAll() {
        logger.info("Attempting to retrieve all sprints from xml!");
        sprints = unmarshall();
        return sprints != null ? sprints.getSprints() : Collections.emptyList();
    }

    @Override
    public void update(Sprint obj) {
        logger.info("Attempting to update sprint with id = " + obj.getId() + " in xml!");

        sprints = unmarshall();

        if (sprints != null) {

            int i = 0;
            for (Sprint sprint : sprints.getSprints()) {
                if (obj.getId().equals(sprint.getId())) {
                    sprints.getSprints().set(i, obj);
                    marshall(sprints);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Sprint get(Integer key) {
        logger.info("Attempting to retrieve sprint with id = " + key + " from xml!");

        Sprint sprint = null;
        sprints = unmarshall();

        if (sprints != null) {
            for (Sprint s : sprints.getSprints()) {
                if (s.getId().equals(key)){
                    sprint = s;
                    break;
                }
            }
        }
        return sprint;
    }

    @Override
    public void delete(Sprint obj) {
        logger.info("Attempting to delete sprint with id = " + obj.getId());

        sprints = unmarshall();

        if (sprints != null) {
            for (Sprint sprint : sprints.getSprints()) {
                if (obj.equals(sprint)) {
                    sprints.getSprints().remove(sprint);
                    marshall(sprints);
                    break;
                }
            }
        }
    }

    @Override
    protected Class<Sprints> getEntityClass() {
        return Sprints.class;
    }
}

