package dao.db.users;

import dao.AbstractDAO;
import dao.db.connection.ConnectionManager;
import entities.DataType;
import entities.users.Customer;
import entities.users.User;
import entities.users.UserRole;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class AdminDAO implements AbstractDAO<User, Integer> {

    private ConnectionManager connectionManager;
    private static final Logger logger = Logger.getLogger(AdminDAO.class);

    private static final String propFileName = "entities-types.properties";

    private static final String entityTableName = "entities";
    private static final String entitiesTypeTableName = "entities_type";
    private static final String attributesTableName = "attributes";
    private static final String attributeTypesTableName = "attribute_types";
    private static final String referencesTableName = "public.references";
    private static final String valuesTableName = "public.values";

    private static final String textValueColumn = "text_value";
    private static final String integerValueColumn = "integer_value";
    private static final String doubleValueColumn = "double_value";
    private static final String dateValueColumn = "date_value";
    private static final String booleanValueColumn = "boolean_value";

    private Properties properties;

    public AdminDAO() {
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
    public void insert(User obj) {
        String insertEntityQuery = "INSERT INTO " + entityTableName + " (entity_type_id) " +
                "VALUES ((SELECT id FROM " + entitiesTypeTableName + " WHERE name = ?));";
        String insertAttributeQuery = "INSERT INTO " + attributesTableName + " (name, type_id) " +
                "VALUES (?, (SELECT id FROM " + attributeTypesTableName + " WHERE name = ?));";
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
        Class clazz = obj.getClass();
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
            insertEntityStatement.setString(1, properties.getProperty("entities.users.user"));
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
                if (entry.getValue() != null) {
                    typeName = entry.getValue().getKey().toString();
                }
                // todo get type_id for null values
                insertAttributeStatement.setString(2, typeName);
                insertAttributeStatement.executeUpdate();
                rs = insertAttributeStatement.getGeneratedKeys();
                rs.next();
                attribute_id = rs.getInt(1);

                PreparedStatement insertReferenceStatement =
                        connection.prepareStatement(insertReferenceQuery);
                insertReferenceStatement.setInt(1, entity_id);
                insertReferenceStatement.setInt(2, attribute_id);
                insertReferenceStatement.executeUpdate();

                PreparedStatement insertValueStatement = null;
                Object param = null;
                try {
                    param = entry.getKey().invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                String column = entry.getValue().getValue();
                boolean isObject = true;

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
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public User get(Integer key) {
        return null;
    }

    @Override
    public User delete(User obj) {
        return null;
    }

    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAO();
        User user = new User();
        user.setUsername("username1");
        user.setLastName("lastname1");
        user.setFirstName("firstname1");
        user.setPassword("password1");
        user.setUserRole(UserRole.ADMIN);
        adminDAO.insert(user);

    }

    private Pair<DataType, String> getReturnType(Method method) {
        if (method.getReturnType().equals(Integer.class)) {
            return new Pair<>(DataType.INTEGER, integerValueColumn);
        } else if (method.getReturnType().equals(Double.TYPE)) {
            return new Pair<>(DataType.DOUBLE, doubleValueColumn);
        } else if (method.getReturnType().equals(String.class)) {
            return new Pair<>(DataType.STRING, textValueColumn);
        } else if (method.getReturnType().equals(Date.class)) {
            return new Pair<>(DataType.DATE, dateValueColumn);
        } else if (method.getReturnType().equals(Boolean.TYPE)) {
            return new Pair<>(DataType.BOOLEAN, booleanValueColumn);
        } else {
            return new Pair<>(DataType.OBJECT, null);
        }
    }
}
