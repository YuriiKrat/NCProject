package dao.serialization.users;

import dao.serialization.AbstractSerializationDAOImpl;
import dao.serialization.projects.ProjectDAO;
import entities.project.Project;
import entities.project.Sprint;
import entities.users.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Denys Vodotiiets.
 */
public class UserDAO extends AbstractSerializationDAOImpl<User, Integer> {

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private static final String fileName = "serialization/entities/users/User.txt";

    public UserDAO() {
        super(fileName);
    }

    @Override
    protected Integer getEntityId(User entity) {
        return entity.getId();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public static void main(String[] args) {
        Project project = new Project(1, "1", new Date(), new Date());
        Sprint sprint1 = new Sprint(1, "1", true);
        Sprint sprint2= new Sprint(2, "1", true);
        Sprint sprint3 = new Sprint(3, "1", true);
        Sprint sprint4 = new Sprint(4, "1", true);
        Sprint sprint5 = new Sprint(5, "1", true);
        Sprint sprint6 = new Sprint(6, "1", true);

        List<Sprint> sprintList = new ArrayList<>();
        sprintList.add(sprint1);
        sprintList.add(sprint2);
        sprintList.add(sprint3);
        sprintList.add(sprint4);
        sprintList.add(sprint5);
        sprintList.add(sprint6);
        project.setSprints(sprintList);

        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.insert(project);
        System.out.println(projectDAO.findAll());

    }

}
