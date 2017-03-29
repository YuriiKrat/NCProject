package dao.serialization;

import dao.AbstractDAO;
import dao.json.AbstractJsonDAOImpl;
import entities.users.Customer;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denys Vodotiiets.
 */
public abstract class AbstractSerializationDAOImpl<T, K> implements AbstractDAO<T, K> {

    private static final Logger logger = Logger.getLogger(AbstractJsonDAOImpl.class);
    private File file;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public AbstractSerializationDAOImpl(String fileName) {
        file = new File(fileName);
    }

    private List<T> receiveEntities() {
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            logger.warn("Failed to read entities from file! " + e.getMessage());
        }
        List<T> entities = new ArrayList<T>();

        if (objectInputStream != null) {
            T temp = null;
            try {
                while ((temp = (T) objectInputStream.readObject()) != null) {
                    entities.add(temp);
                }
            } catch(ClassNotFoundException | IOException e) {
                logger.debug("Failed to read entities from file! " + e.getMessage());
            } finally {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close file!" + e.getMessage());
                }
            }
        }

        return entities;
    }

    private void writeValues(boolean flag, List<T> entities) {
        if (flag) {
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                for (T t : entities) {
                    objectOutputStream.writeObject(t);
                }
            } catch (IOException e) {
                logger.warn("Failed to write entities to file! "
                        + e.getMessage());
            } finally {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close file!" + e.getMessage());
                }
            }
        }
    }

    @Override
    public void insert(T obj) {
        logger.info("Attempting to insert " + obj.getClass().getSimpleName() + " entity in file!");

        boolean unique = false;
        List<T> entities = receiveEntities();
        if (!entities.contains(obj)) {
            entities.add(obj);
            unique = true;
        }
        writeValues(unique, entities);

    }

    @Override
    public List<T> findAll() {
        logger.info("Attempting to receive entities from file!");
        return new ArrayList<>(receiveEntities());
    }

    @Override
    public void update(T obj) {
        boolean updated = false;
        logger.info("Attempting to update " + obj.getClass().getSimpleName() + " entity in json file!");
        List<T> entities = new ArrayList<>(receiveEntities());

        for (int i = 0; i < entities.size(); i++) {
            if (getEntityId(entities.get(i)).equals(getEntityId(obj))) {
                entities.set(i, obj);
                updated = true;
                break;
            }
        }

        writeValues(updated, entities);
    }

    @Override
    public T get(K key) {
        List<T> entities = receiveEntities();

        for(T entity: entities) {
            if (getEntityId(entity).equals(key))
                return entity;
        }

        return null;
    }

    @Override
    public void delete(T obj) {
        boolean deleted = false;
        T entity = null;
        List<T> entities = receiveEntities();

        for (int i = 0; i < entities.size(); i++) {
            if (getEntityId(entities.get(i)).equals(getEntityId(obj))) {
                entities.remove(i);
                deleted = true;
                break;
            }
        }
        writeValues(deleted, entities);

    }


    protected abstract Integer getEntityId(T entity);

    protected abstract Class<T> getEntityClass();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Customer user = new Customer(1, "1", "1", "1", "1");
//        List<Project> projects = new ArrayList<>();
//        projects.add(new Project(1, "1", new Date(), new Date()));
//        user.setProjects(projects);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("test.txt"));
//        objectOutputStream.writeObject(user);
//
//        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("test.txt"));
//        Customer user1 = (Customer) objectInputStream.readObject();


//        System.out.println(user.equals(user1));
    }
}
