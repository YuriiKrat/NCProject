package dao;

import java.sql.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */
public class UserDAO {


    public static void main(String[] args) {

        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/lab3", "user",
                    "123456789");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT id, user_name, password, entity_id FROM user_entity";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String username = rs.getString("user_name");
                String pass = rs.getString("password");
                int entity_id = rs.getInt("entity_id");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", UserName: " + username);
                System.out.print(", Password: " + pass);
                System.out.println(", Entity_id: " + entity_id);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
