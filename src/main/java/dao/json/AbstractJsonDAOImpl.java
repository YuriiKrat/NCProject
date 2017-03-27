package dao.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dao.AbstractDAO;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 27.03.17.
 */
public abstract class AbstractJsonDAOImpl<T, K> implements AbstractDAO<T, K> {

    private static final Logger logger = Logger.getLogger(AbstractJsonDAOImpl.class);
    private File file;

    public AbstractJsonDAOImpl(String fileName) {
        file = new File(fileName);
    }

    @Override
    public void insert(T obj) {
        boolean unique = true;
        List<T> entities = null;
        ObjectMapper readValuesMapper = new ObjectMapper();
        logger.info("Attempting to insert " + obj.getClass().getSimpleName() + " entity in json file!");
        try {
            entities = readValuesMapper.readValue(file, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, getEntityClass()));
        } catch (IOException e) {
            logger.warn("Failed to read " + obj.getClass().getSimpleName() +
                    " entities from json file! " + e.getMessage());
        }

        if (entities != null) {
            for (T user : entities) {
                if ((getEntityId(user).equals(getEntityId(obj)))) {
                    unique = false;
                    break;
                }
            }
            entities.add(obj);
        }
        writeValues(unique, entities);
    }

    @Override
    public List<T> findAll() {
        List<T> entities = null;
        ObjectMapper readValuesMapper = new ObjectMapper();
        try {
            entities = readValuesMapper.readValue(file, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, getEntityClass()));
        } catch (IOException e) {
            logger.warn("Failed to read entities from json file! " + e.getMessage());
        }
        return entities;
    }

    @Override
    public void update(T obj) {
        boolean updated = false;
        List<T> entities = null;
        ObjectMapper readValuesMapper = new ObjectMapper();
        logger.info("Attempting to update " + obj.getClass().getSimpleName() +
                " entity in json file!");
        try {
            entities = readValuesMapper.readValue(file, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, getEntityClass()));
        } catch (IOException e) {
            logger.warn("Failed to read " + obj.getClass().getSimpleName() +
                    " entities from json file! " + e.getMessage());
        }
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                if (getEntityId(entities.get(i)).equals(getEntityId(obj))) {
                    entities.set(i, obj);
                    updated = true;
                    break;
                }
            }
        }
        writeValues(updated, entities);
    }

    @Override
    public T get(K key) {
        List<T> entities = null;
        ObjectMapper readValuesMapper = new ObjectMapper();
        logger.info("Attempting to read entity from json file!");
        try {
            entities = readValuesMapper.readValue(file, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, getEntityClass()));
        } catch (IOException e) {
            logger.warn("Failed to read user's entities from json file! " + e.getMessage());
        }
        if (entities != null) {
            for(T entity: entities) {
                if (getEntityId(entity).equals(key))
                    return entity;
            }
        }
        return null;
    }

    @Override
    public T delete(T obj) {
        boolean deleted = false;
        T entity = null;
        List<T> entities = null;
        ObjectMapper readValuesMapper = new ObjectMapper();

        logger.info("Attempting to delete " + obj.getClass().getSimpleName() +
                " entity from json file!");
        try {
            entities = readValuesMapper.readValue(file, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, getEntityClass()));
        } catch (IOException e) {
            logger.warn("Failed to read " + obj.getClass().getSimpleName() +
                    " entities from json file! " + e.getMessage());
        }

        if(entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                if (getEntityId(entities.get(i)).equals(getEntityId(obj))) {
                    entity = entities.remove(i);
                    deleted = true;
                    break;
                }
            }
            writeValues(deleted, entities);
        }
        return entity;
    }

    private void writeValues(boolean flag, List<T> entities) {
        if(flag) {
            ObjectMapper insertMapper = new ObjectMapper();
            insertMapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                insertMapper.writeValue(file, entities);
            } catch (IOException e) {
                logger.warn("Failed to write entities to json file! " + e.getMessage());
            }
        }
    }

    protected abstract Integer getEntityId(T entity);

    protected abstract Class<T> getEntityClass();
}
