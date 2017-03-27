package dao.collection.users;

import dao.AbstractDAO;
import dao.collection.AbstractDAOImpl;
import entities.project.Project;
import entities.users.Manager;
import entities.users.UserRole;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by win10 on 11.03.2017.
 */
public class ManagerDAO extends AbstractDAOImpl<Manager, Integer> {

    @Override
    public void insert(Manager obj) {
        entities.put(obj.getId(), new Manager(obj));
    }

    @Override
    public void update(Manager obj) {
        entities.put(obj.getId(), obj);
    }

    @Override
    public Manager delete(Manager obj) {
        return entities.remove(obj.getId());
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setFirstName("ZHEKA");
        manager.setLastName("STEPANJUIK");
        manager.setUsername("LOH");
        manager.setUserRole(UserRole.EMPLOYEE.toString());
        Project project = new Project(1, "1", new Date(), new Date());
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project);
        manager.setProjects(projects);
        ManagerDAO managerDAO = new ManagerDAO();
        managerDAO.insert(manager);
        managerDAO.findAll().forEach(System.out::println);
        manager.setUsername("BARAN");
        project.setId(2);
        managerDAO.update(manager);
        managerDAO.findAll().forEach(System.out::println);
    }
}
