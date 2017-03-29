package dao.db.projects;

import dao.db.AbstractDAOImpl;
import dao.db.DataType;
import entities.project.Project;
import entities.project.Sprint;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 13.03.17.
 */
public class ProjectDAO extends AbstractDAOImpl<Project, Integer> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);
    private SprintDAO sprintDAO;

    public ProjectDAO() {
        sprintDAO = new SprintDAO();
    }

    @Override
    public Project get(Integer key) {
        Project project = null;
        try {
            PreparedStatement getAttributesStatement =
                    connection.prepareStatement(getAttributesQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            getAttributesStatement.setInt(1, key);
            ResultSet resultSet = getAttributesStatement.executeQuery();
            if (resultSet.last()) {
                if (resultSet.getRow() > 0) {
                    project = new Project();
                    project.setId(key);
                }
                resultSet.beforeFirst();
            }
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
                    Class clazz = project.getClass();
                    try {
                        Method method = clazz.getMethod("set" + attribute_name, classType);
                        if (DataType.INTEGER.toString().equals(type_name)) {
                           method.invoke(project, value.getInt(integerValueColumn));
                        } else if (DataType.STRING.toString().equals(type_name)) {
                            method.invoke(project, value.getString(textValueColumn));
                        } else if (DataType.BOOLEAN.toString().equals(type_name)) {
                            method.invoke(project, value.getBoolean(booleanValueColumn));
                        } else if (DataType.DATE.toString().equals(type_name)) {
                            method.invoke(project, value.getDate(dateValueColumn));
                        } else if (DataType.DOUBLE.toString().equals(type_name)) {
                            method.invoke(project, value.getDouble(doubleValueColumn));
                        } else {
                            List<Sprint> sprints = new ArrayList<>();
                            do {
                                sprints.add(sprintDAO.get(value.getInt(entityIdColumn)));
                            } while (value.next());
                            method.invoke(project, sprints);
                        }

                    } catch (IllegalAccessException | InvocationTargetException |
                            NoSuchMethodException e) {
                        logger.error("Failed to set value! " + e.getMessage());
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve sprint from db! " + e.getMessage());
        }
        return project;
    }

    @Override
    protected Class getEntityClass() {
        return Project.class;
    }

    @Override
    protected Integer getEntityId(Project entity) {
        return entity.getId();
    }
}
