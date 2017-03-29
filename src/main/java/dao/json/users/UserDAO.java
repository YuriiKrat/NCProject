package dao.json.users;

import dao.json.AbstractJsonDAOImpl;
import dao.json.companies.CompanyDAO;
import entities.Company;
import entities.project.Project;
import entities.project.Sprint;
import entities.project.Task;
import entities.users.User;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public class UserDAO extends AbstractJsonDAOImpl<User, Integer> {

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private static final String FILE_NAME = "json/entities/users/Users.txt";
    private File file;

    public UserDAO() {
        super(FILE_NAME);
    }

    @Override
    protected Integer getEntityId(User entity) {
        return entity.getId();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public static void main(String[] args) throws JAXBException {
        Company company = new Company(1, "1");
        List<Project> projectList = new ArrayList<>();

        Project project = new Project(1, "1", new Date(), new Date());
        projectList.add(project);

        List<Sprint> sprints = new ArrayList<>();
        Sprint sprint = new Sprint(1, "1", true);
        sprints.add(sprint);

        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, "1", 1, new Date(), "1", "1");
        tasks.add(task1);
        Task subb = new Task(2, "2", 2, new Date(), "2", "2");
        Task inff = new Task(3, "3", 3, new Date(), "3", "3");
        List<Task> sub = new ArrayList<>();
        List<Task> inf = new ArrayList<>();

        task1.setSubtasks(sub);
        sub.add(subb);
        inf.add(inff);
        task1.setInfluencedTasks(inf);
        sprint.setTasks(tasks);
        project.setSprints(sprints);
        company.setProjects(projectList);


        CompanyDAO companyDAO = new CompanyDAO();
        companyDAO.insert(company);

        dao.xml.companies.CompanyDAO companyDAO1 = new dao.xml.companies.CompanyDAO();
        companyDAO1.insert(company);

        dao.collection.company.CompanyDAO companyDAO2 = new dao.collection.company.CompanyDAO();
        companyDAO2.insert(company);
    }

}
