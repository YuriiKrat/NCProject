package entities.users;

import javax.xml.bind.annotation.*;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.02.17.
 */

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    protected Integer id;

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected UserRole userRole;

    public User() {

    }


    public Integer getId() {
        return id;
    }

//    @XmlAttribute
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

//    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

//    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

//    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

//    @XmlElement
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

//    @XmlElement
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

//    @Override
//    protected User clone() throws CloneNotSupportedException {
//        User user = new User();
//        user.setUsername(username);
//        user.setLastName(lastName);
//        user.setFirstName(firstName);
//        user.setId(id);
//        user.setPassword(password);
//        return user;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
