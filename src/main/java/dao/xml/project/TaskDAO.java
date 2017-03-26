package dao.xml.project;

import dao.AbstractDAO;
import entities.project.Task;
import entities.project.TaskStatus;
import entities.users.Qualification;
import org.apache.log4j.Logger;

import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 14.03.17.
 */

public class TaskDAO implements AbstractDAO<Task, Integer> {

    private static final String fileName = "xml/entities/project/Tasks.xml";
    private File file;
    private JAXBContext jaxbContext;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;
    private Tasks tasks;

    private static final Logger logger = Logger.getLogger(TaskDAO.class);

    public TaskDAO() throws JAXBException {
        tasks = new Tasks();
        jaxbContext = JAXBContext.newInstance(Tasks.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        file = new File(fileName);

    }

    @Override
    public void insert(Task obj) {
        try {
            if (file.exists()) {
                Source source = new StreamSource(new File(fileName));
                JAXBElement<Tasks> root = jaxbUnmarshaller.unmarshal(source, Tasks.class);
                tasks = root.getValue();
                System.out.println(tasks);
            }
        } catch (JAXBException e) {
            logger.warn("Can not insert value into xml-file! " + e.getMessage());
        }
        tasks.getTasks().add(obj);
        logger.error(tasks);
        try {
            jaxbMarshaller.marshal(tasks, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public void update(Task obj) {

    }

    @Override
    public Task get(Integer key) {
        return null;
    }

    @Override
    public Task delete(Task obj) {
        return null;
    }

    public static void main(String[] args) throws JAXBException, ParserConfigurationException {
        Task task1 = new Task();
        task1.setId(2);
        task1.setName("Task1");
        task1.setStartDate(new Date());
        task1.setEstimate(10);
        task1.setPreferredQualification(Qualification.MIDDLE.toString());
        task1.setStatus(TaskStatus.OPEN.toString());

        Task task2 = new Task();
        task2.setId(2);
        task2.setName("Task2");
        task2.setStartDate(new Date());
        task2.setEstimate(10);
        task2.setPreferredQualification(Qualification.MIDDLE.toString());
        task2.setStatus(TaskStatus.OPEN.toString());

        List<Task> subtasks = new ArrayList<>();
        subtasks.add(task2);
        task1.setSubtasks(subtasks);

        List<Task> subtasks2 = new ArrayList<>();
        subtasks2.add(task1);
        task2.setSubtasks(subtasks);

        TaskDAO taskDAO = new TaskDAO();
        taskDAO.insert(task1);


    }
}