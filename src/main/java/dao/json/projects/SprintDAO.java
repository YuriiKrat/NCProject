package dao.json.projects;

import dao.json.AbstractJsonDAOImpl;
import entities.project.Sprint;
import org.apache.log4j.Logger;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class SprintDAO extends AbstractJsonDAOImpl<Sprint, Integer> {

    private static final Logger logger = Logger.getLogger(SprintDAO.class);

    private static final String FILE_NAME = "json/entities/projects/Sprints.txt";

    public SprintDAO() {
        super(FILE_NAME);
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
