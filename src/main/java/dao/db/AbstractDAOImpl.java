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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.03.17.
 */
public abstract class AbstractDAOImpl<T, K> implements AbstractDAO<T, K>{

    protected ConnectionManager connectionManager;
    private static final Logger logger = Logger.getLogger(AbstractDAOImpl.class);

    private static final String propFileName = "entities-types.properties";

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

    private Properties properties;

    public AbstractDAOImpl() {
        connectionManager = new ConnectionManager();
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
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

        Connection connection = connectionManager.getConnection();
        int entity_id;
        int attribute_id;
        Class clazz = getEntityClass();
        Map<Method, Pair<DataType, String>> attributes = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith("get") && !method.getName().startsWith("getId")) {
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
                                insertValueStatement.setString(2, param.toString());
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
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to close connection! " + e.getMessage());
        }

    }

    @Override
    public void update(T obj) {
        String getAttributesQuery = "SELECT " + attributesTableName + ".id, " +
                attributesTableName + ".name, " +
                attributesTableName + ".is_multiple, " +
                attributeTypesTableName + ".name AS type_name FROM " + attributesTableName +
                " INNER JOIN " + referencesTableName +
                " ON " + referencesTableName  + ".attribute_id = "
                + attributesTableName + ".id LEFT JOIN " + attributeTypesTableName +
                " ON " + attributeTypesTableName  + ".id = "
                + attributesTableName + ".type_id WHERE entity_id = ?;";

        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement getAttributesStatement = connection.prepareStatement(getAttributesQuery);
            getAttributesStatement.setInt(1, getEntityId(obj));
            ResultSet resultSet = getAttributesStatement.executeQuery();
            while (resultSet.next()) {
                try {
                    if(!resultSet.getBoolean("is_multiple")) {
                        setValuesWhenUpdate(connection, resultSet, obj);

                    } else {
                        Method method = getEntityClass().getMethod("get" + resultSet.getString("name"));
                        List values = (List) method.invoke(obj);
                        for(Object value: values) {

                        }
                    }
                } catch (InvocationTargetException | NoSuchMethodException |
                        IllegalAccessException | ClassNotFoundException e) {
                    logger.warn("Failed to retrieve entity params! " + e.getMessage());
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

    private void setValuesWhenUpdate(Connection connection, ResultSet resultSet, T obj)
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

    private void setCollectionValuesWhenUpdate() {
//        PreparedStatement statement = null;
//        PreparedStatement getObjectTypeStatement = connection.prepareStatement(getEntityTypeQuery);
//        getObjectTypeStatement.setInt(1, resultSet.getInt("id"));
//        ResultSet result = getObjectTypeStatement.executeQuery();
//        result.next();
//        String entityTypeName = result.getString("name");
//        statement = connection.prepareStatement(setObjectValuesQuery);
//        statement.setInt(1, getObjectId(entityTypeName, method, obj));
//        statement.setInt(2, resultSet.getInt("id"));
//        statement.executeUpdate();
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

    protected abstract Class getEntityClass();

    protected abstract Integer getEntityId(T entity);

    protected abstract Class getFieldClass(Object field);

}
