package dao.db;

import dao.db.connection.ConnectionManager;
import entities.users.UserRole;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class InitDB {

    private ConnectionManager connectionManager;
    private static final Logger logger = Logger.getLogger(InitDB.class);
    private static final String entitiesType = "entities_type";
    private static final String attributeType = "attribute_types";

    private void insertEntitiesTypes(String name) {
        String query = "INSERT INTO " + entitiesType + " (name) VALUES (?);";
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can not execute query when insert into " + entitiesType + "!\n"
            + e.getMessage());
        }
    }

    private void insertAttributeTypes(String name) {
        String query = "INSERT INTO " + attributeType + " (name) VALUES (?);";
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can not execute query when insert into " + entitiesType + "!\n"
                    + e.getMessage());
        }
    }

    private void insertUserRoles(UserRole userRole) {
        int entity_type_id;
        int attribute_type_id;
        String query1 = "INSERT INTO entities (entity_type_id) VALUES ((SELECT id FROM "
                + entitiesType + " WHERE name=?));";
        String query2 = "INSERT INTO attributes (name, type_id) VALUES (?, (SELECT id FROM attribute_types " +
                "WHERE name=?));";
        String query3 = "INSERT INTO public.references (entity_id, attribute_id) VALUES (?, ?);";
        String query4 = "INSERT INTO public.values (attribute_id, text_value) VALUES (?, ?);";
        String query5 = "INSERT INTO values (attribute_id, text_value) VALUES (?, ?);";
        String query6 = "INSERT INTO values (attribute_id, text_value) VALUES (?, ?);";

        int attribute_id;
        int entity_id;
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement statement1 = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement3 = connection.prepareStatement(query3);
            PreparedStatement statement4 = connection.prepareStatement(query4)) {

            statement1.setString(1, "entities.users.UserRole");
            statement1.executeUpdate();
            ResultSet rs = statement1.getGeneratedKeys();
            rs.next();
            entity_id = rs.getInt(1);

            statement2.setString(1, "role");
            statement2.setString(2, DataType.STRING.toString());
            statement2.executeUpdate();
            rs = statement2.getGeneratedKeys();
            rs.next();
            attribute_id = rs.getInt(1);

            statement3.setInt(1, entity_id);
            statement3.setInt(2, attribute_id);
            statement3.executeUpdate();

            statement4.setInt(1, attribute_id);
            statement4.setString(2, userRole.toString());
            statement4.executeUpdate();


        } catch (SQLException e) {
            logger.warn("Can not execute query when insert into " + entitiesType + "!\n"
                    + e.getMessage());
        }
    }

    public InitDB() {
       connectionManager = new ConnectionManager();
    }

    public static void main(String[] args) {
        InitDB db = new InitDB();

//        db.insertEntitiesTypes("java.util.LinkedList");
//        db.insertEntitiesTypes("java.util.ArrayList");
//        db.insertEntitiesTypes("entities.users.Manager");
//        db.insertEntitiesTypes("entities.users.Employee");
//        db.insertEntitiesTypes("entities.users.Customer");
//
//        db.insertEntitiesTypes("entities.users.User");
//        db.insertEntitiesTypes("entities.projects.Project");
//        db.insertEntitiesTypes("entities.projects.Sprint");
//        db.insertEntitiesTypes("entities.projects.Task");
//        db.insertEntitiesTypes("entities.projects.TaskStatus");
////
//        db.insertEntitiesTypes("entities.Company");
//
//        db.insertAttributeTypes(DataType.BOOLEAN.toString());
//        db.insertAttributeTypes(DataType.DATE.toString());
//        db.insertAttributeTypes(DataType.DOUBLE.toString());
//        db.insertAttributeTypes(DataType.INTEGER.toString());
//        db.insertAttributeTypes(DataType.STRING.toString());


    }
}
