package dao.db;

import dao.AbstractDAO;
import dao.db.connection.ConnectionManager;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.03.17.
 */
public abstract class AbstractDAOImpl<T, K> implements AbstractDAO<T, K>{

    private static final Logger logger = Logger.getLogger(AbstractDAOImpl.class);

    private static final String PROP_FILE_NAME = "entities-types.properties";

    protected Connection connection;
    private ConnectionManager connectionManager;

    protected static final String entityTableName = "entities";
    protected static final String entitiesTypeTableName = "entities_type";
    protected static final String attributesTableName = "attributes";
    protected static final String attributeBindsTableName = "attributes_binds";
    protected static final String attributeTypesTableName = "attribute_types";
    protected static final String referencesTableName = "public.references";
    protected static final String valuesTableName = "public.values";

    protected static final String textValueColumn = "text_value";
    protected static final String integerValueColumn = "integer_value";
    protected static final String doubleValueColumn = "double_value";
    protected static final String dateValueColumn = "date_value";
    protected static final String booleanValueColumn = "boolean_value";
    protected static final String entityIdColumn = "entity_id";

    protected static String getAttributesQuery = "SELECT " + attributesTableName + ".id, " +
            attributesTableName + ".name, " +
            attributesTableName + ".is_multiple, " +
            attributeTypesTableName + ".name AS type_name FROM " + attributesTableName +
            " INNER JOIN " + referencesTableName +
            " ON " + referencesTableName  + ".attribute_id = "
            + attributesTableName + ".id LEFT JOIN " + attributeTypesTableName +
            " ON " + attributeTypesTableName  + ".id = "
            + attributesTableName + ".type_id WHERE entity_id = ?;";
    private static String deleteValuesQuery = "DELETE FROM " + valuesTableName + " WHERE attribute_id = ?;";

    private Properties properties;

    public AbstractDAOImpl() {
        connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Config failed!" + e.getMessage());
        }
    }

    @Override
    public void insert(T obj) {
        String insertEntityQuery = "INSERT INTO " + entityTableName + " (entity_type_id) " +
                "VALUES ((SELECT id FROM " + entitiesTypeTableName + " WHERE name = ?));";
        String insertAttributeQuery = "INSERT INTO " + attributesTableName +
                " (name, type_id, is_multiple) VALUES (?, (SELECT id FROM " + attributeTypesTableName +
                " WHERE name = ?), ?);";
        String insertAttributeBindsQuery = "INSERT INTO " + attributeBindsTableName +
                " (entity_type_id, attribute_id) " + "VALUES ((SELECT id FROM " +
                entitiesTypeTableName + " WHERE name = ?), ?);";
        String insertReferenceQuery = "INSERT INTO " + referencesTableName + " (entity_id, attribute_id) " +
                "VALUES (?, ?);";
        String insertIntegerValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, " +
                integerValueColumn + ") VALUES (?, ?);";
        String insertDoubleValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, " +
                doubleValueColumn + ") VALUES (?, ?);";
        String insertStringValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, " +
                textValueColumn + ") VALUES (?, ?);";
        String insertBooleanValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, " +
                booleanValueColumn + ") VALUES (?, ?);";
        String insertDateValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, " +
                dateValueColumn + ") VALUES (?, ?);";
        String insertObjectValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id)" +
                " VALUES (?);";

        int entity_id;
        int attribute_id;
        Class clazz = getEntityClass();
        Map<Method, Pair<DataType, String>> attributes = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("get") && !method.getName().startsWith("getId")
                    && !method.getName().startsWith("getClass")) {
                attributes.put(method, getReturnType(method));
            }
        }
        try {
            connection.setAutoCommit(false);
            PreparedStatement insertEntityStatement =
                    connection.prepareStatement(insertEntityQuery, Statement.RETURN_GENERATED_KEYS);
            insertEntityStatement.setString(1, properties.getProperty(clazz.getName().toLowerCase()));
            insertEntityStatement.executeUpdate();
            ResultSet rs = insertEntityStatement.getGeneratedKeys();
            rs.next();
            entity_id = rs.getInt(1);
            for(Map.Entry<Method, Pair<DataType, String>> entry: attributes.entrySet()) {

                PreparedStatement insertAttributeStatement =
                        connection.prepareStatement(insertAttributeQuery, Statement.RETURN_GENERATED_KEYS);
                String attribute = entry.getKey().getName().substring(3, entry.getKey().getName().length());
                insertAttributeStatement.setString(1, attribute);
                String typeName = null;
                boolean isList = false;

                if (entry.getValue() != null) {
                    typeName = entry.getValue().getKey().toString();
                    if (entry.getValue().getKey().equals(DataType.LIST)) {
                        isList = true;
                    }
                }

                insertAttributeStatement.setString(2, typeName);
                insertAttributeStatement.setBoolean(3, isList);
                insertAttributeStatement.executeUpdate();
                rs = insertAttributeStatement.getGeneratedKeys();
                rs.next();
                attribute_id = rs.getInt(1);

                PreparedStatement insertReferenceStatement =
                        connection.prepareStatement(insertReferenceQuery);
                insertReferenceStatement.setInt(1, entity_id);
                insertReferenceStatement.setInt(2, attribute_id);
                insertReferenceStatement.executeUpdate();

                Object param = null;
                boolean isObject = true;

                try {
                    param = entry.getKey().invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                if (!isList) {
                    PreparedStatement insertValueStatement = null;

                    String column = entry.getValue().getValue();

                    if (column != null) {
                        switch (column) {
                            case textValueColumn:
                                insertValueStatement = connection.prepareStatement(insertStringValuesQuery);
                                insertValueStatement.setString(2, (String)param);
                                break;
                            case integerValueColumn:
                                insertValueStatement = connection.prepareStatement(insertIntegerValuesQuery);
                                insertValueStatement.setInt(2, (Integer) param);
                                break;
                            case doubleValueColumn:
                                insertValueStatement = connection.prepareStatement(insertDoubleValuesQuery);
                                insertValueStatement.setDouble(2, (Double) param);
                                break;
                            case dateValueColumn:
                                insertValueStatement = connection.prepareStatement(insertDateValuesQuery);
                                insertValueStatement.setDate(2, (java.sql.Date) param);
                                break;
                            case booleanValueColumn:
                                insertValueStatement = connection.prepareStatement(insertBooleanValuesQuery);
                                insertValueStatement.setBoolean(2, (Boolean) param);
                                break;
                        }
                        isObject = false;
                    }
                    if (isObject) {
                        insertValueStatement = connection.prepareStatement(insertObjectValuesQuery);
                    }

                    insertValueStatement.setInt(1, attribute_id);
                    insertValueStatement.executeUpdate();
                }
                if (isObject || isList) {
                    PreparedStatement insertAttrbiteBindsStatement =
                            connection.prepareStatement(insertAttributeBindsQuery);
                    insertAttrbiteBindsStatement.setString(1, param.getClass().getName());
                    insertAttrbiteBindsStatement.setInt(2, attribute_id);
                    insertAttrbiteBindsStatement.executeUpdate();
                }

            }
            connection.commit();

        } catch (SQLException e) {
            logger.error("Failed to insert entity into db! " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Failed to do rollback! " + e1.getMessage());
            }
        }

    }

    @Override
    public void update(T obj) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement getAttributesStatement = connection.prepareStatement(getAttributesQuery);
            getAttributesStatement.setInt(1, getEntityId(obj));
            ResultSet resultSet = getAttributesStatement.executeQuery();
            while (resultSet.next()) {
                try {
                    if(!resultSet.getBoolean("is_multiple")) {
                        setValuesWhenUpdate(resultSet, obj);

                    } else {
                        Method method = getEntityClass().getMethod("get" + resultSet.getString("name"));
                        List values = (List) method.invoke(obj);
                        Integer attribute_id = resultSet.getInt("id");
                        PreparedStatement deleteCollectionValuesStatement =
                                connection.prepareStatement(deleteValuesQuery);
                        deleteCollectionValuesStatement.setInt(1, attribute_id);
                        deleteCollectionValuesStatement.executeUpdate();
                        for(Object value: values) {
                            Method innerMethod = value.getClass().getMethod("getId");
                            Integer id = (Integer) innerMethod.invoke(value);
                            setCollectionValuesWhenUpdate(id, attribute_id);
                        }
                    }
                } catch (InvocationTargetException | NoSuchMethodException |
                        IllegalAccessException | ClassNotFoundException e) {
                    logger.warn("Failed to retrieve entity params! " + e.getMessage());
                }
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("Failed to update entity in db! " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Failed to do rollback! " + e1.getMessage());
            }
        }

    }

    @Override
    public void delete(T obj) {
        String getReferencesQuery = "SELECT attribute_id FROM " + referencesTableName +
                " WHERE entity_id = ?;";
        String deleteAttributeBindsQuery = "DELETE FROM " + attributeBindsTableName +
                " WHERE attribute_id = ?";
        String deleteReferencesQuery = "DELETE FROM " + referencesTableName +
                " WHERE entity_id = ?";
        String deleteAttributesQuery = "DELETE FROM " + attributesTableName +
                " WHERE id = ?";
        String deleteEntityQuery = "DELETE FROM " + entityTableName +
                " WHERE id = ?";

        try {
            connection.setAutoCommit(false);
            PreparedStatement getReferencesStatement =
                    connection.prepareStatement(getReferencesQuery);
            getReferencesStatement.setInt(1, getEntityId(obj));
            ResultSet resultSet = getReferencesStatement.executeQuery();
            executeDeleteStatement(deleteReferencesQuery, getEntityId(obj));
            while (resultSet.next()) {
                Integer attribute_id = resultSet.getInt("attribute_id");
                executeDeleteStatement(deleteValuesQuery, attribute_id);
                executeDeleteStatement(deleteAttributeBindsQuery, attribute_id);
                executeDeleteStatement(deleteAttributesQuery, attribute_id);
            }
            executeDeleteStatement(deleteEntityQuery, getEntityId(obj));
            connection.commit();

        } catch (SQLException e) {
            logger.error("Failed to delete entity from db! " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Failed to do rollback! " + e1.getMessage());
            }
        }

    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String getAllEntitiesIdQuery = "SELECT " + entityTableName + ".id FROM " +
                entityTableName + " INNER JOIN " + entitiesTypeTableName +
                " ON " + entityTableName + ".entity_type_id = " +
                entitiesTypeTableName + ".id WHERE " +
                entitiesTypeTableName + ".name = ?;";
        try {
            PreparedStatement getAllEntitiesIdStatement =
                    connection.prepareStatement(getAllEntitiesIdQuery);
            getAllEntitiesIdStatement.setString(1, getEntityClass().getName());
            ResultSet resultSet = getAllEntitiesIdStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(get((K)Integer.valueOf(resultSet.getInt("id"))));
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve all entities from db! " + e.getMessage());
        }
        return entities;
    }

    /**
     * Updates entity attributes which are not of collection type
     *
     * @param resultSet
     * @param obj
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    private void setValuesWhenUpdate(ResultSet resultSet, T obj)
            throws SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException {

        String setStringValuesQuery = "UPDATE " + valuesTableName + " SET text_value = ? WHERE attribute_id = ?;";
        String setIntegerValuesQuery = "UPDATE " + valuesTableName + " SET integer_value = ? WHERE attribute_id = ?;";
        String setDoubleValuesQuery = "UPDATE " + valuesTableName + " SET double_value = ? WHERE attribute_id = ?;";
        String setDateValuesQuery = "UPDATE " + valuesTableName + " SET date_value = ? WHERE attribute_id = ?;";
        String setBooleanValuesQuery = "UPDATE " + valuesTableName + " SET boolean_value = ? WHERE attribute_id = ?;";
        String setObjectValuesQuery = "UPDATE " + valuesTableName + " SET entity_id = ? WHERE attribute_id = ?;";
        String getEntityTypeQuery = "SELECT " + entitiesTypeTableName + ".name FROM" + attributeBindsTableName +
                "INNER JOIN " + entitiesTypeTableName + " ON entity_type_id = " + attributesTableName + ".id" +
                " WHERE attribute_id = ?;";

        String type = resultSet.getString("type_name");
        PreparedStatement statement = null;
        Method method = getEntityClass().getMethod("get" + resultSet.getString("name"));
        if (type != null) {
            if (DataType.STRING.toString().equals(type)) {
                statement = connection.prepareStatement(setStringValuesQuery);
                statement.setString(1, (String)method.invoke(obj));
            } else if (DataType.INTEGER.toString().equals(type)) {
                statement = connection.prepareStatement(setIntegerValuesQuery);
                statement.setInt(1, (Integer) method.invoke(obj));
            } else if (DataType.DOUBLE.toString().equals(type)) {
                statement = connection.prepareStatement(setDoubleValuesQuery);
                statement.setDouble(1, (Double) method.invoke(obj));
            } else if (DataType.DATE.toString().equals(type)) {
                statement = connection.prepareStatement(setDateValuesQuery);
                statement.setDate(1, (java.sql.Date) method.invoke(obj));
            } else if (DataType.BOOLEAN.toString().equals(type)) {
                statement = connection.prepareStatement(setBooleanValuesQuery);
                statement.setBoolean(1, (Boolean) method.invoke(obj));
            }
        } else {
            PreparedStatement getObjectTypeStatement = connection.prepareStatement(getEntityTypeQuery);
            getObjectTypeStatement.setInt(1, resultSet.getInt("id"));
            ResultSet result = getObjectTypeStatement.executeQuery();
            result.next();
            String entityTypeName = result.getString("name");
            statement = connection.prepareStatement(setObjectValuesQuery);
            statement.setInt(1, getObjectId(entityTypeName, method, obj));
        }
        statement.setInt(2, resultSet.getInt("id"));
        statement.executeUpdate();
    }

    /**
     * Updates entity attribute which is collection.
     * So updates values from collection in value table
     *
     * @param entityId entity id reference
     * @param attribute_id attribute reference
     */
    private void setCollectionValuesWhenUpdate(Integer entityId, Integer attribute_id) {
        String insertCollectionValuesQuery = "INSERT INTO " + valuesTableName + " (attribute_id, entity_id)" +
                " VALUES (?, ?);";
        try {
            PreparedStatement insertCollectionValuesStatement =
                    connection.prepareStatement(insertCollectionValuesQuery);
            insertCollectionValuesStatement.setInt(1, attribute_id);
            insertCollectionValuesStatement.setInt(2, entityId);
            insertCollectionValuesStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to insert collection entity into db! " + e.getMessage());
        }
    }

    private void executeDeleteStatement(String query, Integer attribute_id) throws SQLException {
        PreparedStatement deleteStatement =
                connection.prepareStatement(query);
        deleteStatement.setInt(1, attribute_id);
        deleteStatement.executeUpdate();
    }

    private Integer getObjectId(String name, Method method, T obj)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = Class.forName(name);
        Method innerMethod = clazz.getMethod("getId");
        return (Integer) innerMethod.invoke(method.invoke(obj));
    }

    private Pair<DataType, String> getReturnType(Method method) {

        if (method.getReturnType().equals(Integer.class)) {
            return new Pair<>(DataType.INTEGER, integerValueColumn);
        } else if (method.getReturnType().equals(Double.TYPE)) {
            return new Pair<>(DataType.DOUBLE, doubleValueColumn);
        } else if (method.getReturnType().equals(String.class)) {
            return new Pair<>(DataType.STRING, textValueColumn);
        } else if (method.getReturnType().equals(java.util.Date.class)) {
            return new Pair<>(DataType.DATE, dateValueColumn);
        } else if (method.getReturnType().equals(Boolean.TYPE)) {
            return new Pair<>(DataType.BOOLEAN, booleanValueColumn);
        } else if (method.getReturnType().equals(List.class)) {
            return new Pair<>(DataType.LIST, null);
        } else {
            return new Pair<>(DataType.OBJECT, null);
        }
    }

    protected Pair<String, Class> getTypeInfo(String type) {
        Class clazz = null;
        if (DataType.INTEGER.toString().equals(type)) {
            return new Pair<>(integerValueColumn, Integer.class);
        } else if (DataType.STRING.toString().equals(type)) {
            return new Pair<>(textValueColumn, String.class);
        } else if (DataType.BOOLEAN.toString().equals(type)) {
            return new Pair<>(booleanValueColumn, Boolean.class);
        } else if (DataType.DATE.toString().equals(type)) {
            return new Pair<>(dateValueColumn, java.util.Date.class);
        } else if (DataType.DOUBLE.toString().equals(type)) {
            return new Pair<>(doubleValueColumn, Double.class);
        }
        return new Pair<>(null, null);

    }

    protected void setFields(ResultSet resultSet, T obj, AbstractDAO dao) throws SQLException {
        while (resultSet.next()) {
            Integer attribute_id = resultSet.getInt("id");
            String attribute_name = resultSet.getString("name");
            String type_name = resultSet.getString("type_name");
            Pair<String, Class> pair = getTypeInfo(type_name);
            String column = pair.getKey();
            Class classType = pair.getValue();
            if (column == null) {
                column = entityIdColumn;
            }
            if (classType == null) {
                classType = List.class;
            }
            String getValueQuery = "SELECT " + column + " FROM " + valuesTableName +
                    " WHERE attribute_id = ?;";
            PreparedStatement getValueStatement =
                    connection.prepareStatement(getValueQuery);
            getValueStatement.setInt(1, attribute_id);
            ResultSet value = getValueStatement.executeQuery();
            if (value.next()) {
                Class clazz = obj.getClass();
                try {
                    Method method = clazz.getMethod("set" + attribute_name, classType);
                    if (DataType.INTEGER.toString().equals(type_name)) {
                        method.invoke(obj, value.getInt(integerValueColumn));
                    } else if (DataType.STRING.toString().equals(type_name)) {
                        method.invoke(obj, value.getString(textValueColumn));
                    } else if (DataType.BOOLEAN.toString().equals(type_name)) {
                        method.invoke(obj, value.getBoolean(booleanValueColumn));
                    } else if (DataType.DATE.toString().equals(type_name)) {
                        method.invoke(obj, value.getDate(dateValueColumn));
                    } else if (DataType.DOUBLE.toString().equals(type_name)) {
                        method.invoke(obj, value.getDouble(doubleValueColumn));
                    } else {
                        List<Object> list = new ArrayList<>();
                        do {
                            list.add(dao.get(value.getInt(entityIdColumn)));
                        } while (value.next());
                        method.invoke(obj, list);
                    }

                } catch (IllegalAccessException | InvocationTargetException |
                        NoSuchMethodException e) {
                    logger.error("Failed to set value! " + e.getMessage());
                }
            }
        }
    }

    protected abstract Class getEntityClass();

    protected abstract Integer getEntityId(T entity);

    public void closeConnection() {
        connectionManager.closeConnection();
    }

}
