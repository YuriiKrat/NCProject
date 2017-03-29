package dao.serialization.projects;

import dao.serialization.AbstractSerializationDAOImpl;
import entities.project.Sprint;
import org.apache.log4j.Logger;

/**
 * Created by Denys Vodotiiets.
 */
public class SprintDAO extends AbstractSerializationDAOImpl<Sprint, Integer> {

    private static final Logger logger = Logger.getLogger(SprintDAO.class);

    private static final String fileName = "serialization/entities/projects/Company.txt";

    public SprintDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(Sprint entity) {
        return entity.getId();
    }

    @Override
    protected Class<Sprint> getEntityClass() {
        return Sprint.class;
    }
}
