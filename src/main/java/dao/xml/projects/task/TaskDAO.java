package dao.xml.projects.task;

import dao.AbstractDAO;
import dao.xml.XmlWriter;
import entities.project.Task;
import entities.project.TaskStatus;
import entities.users.Qualification;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */
public class TaskDAO extends XmlWriter<Tasks> implements AbstractDAO<Task, Integer> {

    private static final Logger logger = Logger.getLogger(TaskDAO.class);

    private static final String fileName = "xml/entities/projects/Task.xml";
    private Tasks tasks;

    public TaskDAO() throws JAXBException {
        super(fileName);
        tasks = new Tasks();
    }

    @Override
    public void insert(Task obj) {
        logger.info("Attempting to insert task with id = " + obj.getId());
        boolean unique = true;
        tasks = unmarshall();
        if (tasks == null) {
            tasks = new Tasks();
        }
        for (int i = 0; i < tasks.getTasks().size(); i++) {
            if (obj.getId().equals(tasks.getTasks().get(i).getId())) {
                unique = false;
                break;
            }
        }
        if (unique) {
            tasks.getTasks().add(obj);
            marshall(tasks);
        } else {
            logger.warn("Error while inserting task! User with id = " + obj.getId() + " already exists!");
        }
    }

    @Override
    public List<Task> findAll() {
        logger.info("Attempting to retrieve all tasks from xml!");
        tasks = unmarshall();
        return tasks != null ? tasks.getTasks() : Collections.emptyList();
    }

    @Override
    public void update(Task obj) {
        logger.info("Attempting to update task with id = " + obj.getId() + " in xml!");

        tasks = unmarshall();

        if (tasks != null) {

            int i = 0;
            for (Task task : tasks.getTasks()) {
                if (obj.getId().equals(task.getId())) {
                    tasks.getTasks().set(i, obj);
                    marshall(tasks);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public Task get(Integer key) {
        logger.info("Attempting to retrieve task with id = " + key + " from xml!");

        Task task = null;
        tasks = unmarshall();

        if (tasks != null) {
            for (Task t : tasks.getTasks()) {
                if (t.getId().equals(key)){
                    task = t;
                    break;
                }
            }
        }
        return task;
    }

    @Override
    public void delete(Task obj) {
        logger.info("Attempting to delete task with id = " + obj.getId());

        tasks = unmarshall();

        if (tasks != null) {
            for (Task task : tasks.getTasks()) {
                if (obj.equals(task)) {
                    tasks.getTasks().remove(task);
                    marshall(tasks);
                    break;
                }
            }
        }
    }

    @Override
    protected Class<Tasks> getEntityClass() {
        return Tasks.class;
    }


    public static void main(String[] args) throws JAXBException, ParserConfigurationException {
        Task task1 = new Task();
        task1.setId(2);
        task1.setName("Task1");
        task1.setStartDate(new Date());
        task1.setEstimate(10);
        task1.setPreferredQualification(Qualification.MIDDLE.toString());
        task1.setTaskStatus(TaskStatus.OPEN.toString());

        Task task2 = new Task();
        task2.setId(2);
        task2.setName("Task2");
        task2.setStartDate(new Date());
        task2.setEstimate(10);
        task2.setPreferredQualification(Qualification.MIDDLE.toString());
        task2.setTaskStatus(TaskStatus.OPEN.toString());

        List<Task> subtasks = new ArrayList<>();
        subtasks.add(task2);
        task1.setSubtasks(subtasks);
//
//        List<Task> subtasks2 = new ArrayList<>();
//        subtasks2.add(task1);
//        task2.setSubtasks(subtasks);

        TaskDAO taskDAO = new TaskDAO();
        taskDAO.insert(task1);


    }
}