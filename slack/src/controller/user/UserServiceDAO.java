package controller.user;

import model.user.User;
import controller.AbstractServiceDAO;
import tool.DataManipulator;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * UserServiceDAO : provide services related to the User
 */
public class UserServiceDAO extends AbstractServiceDAO {

    /**
     * OK
     *
     * @param inputedEmail             entered email
     * @param inputedPassword          entered password
     * @param confirmedInputedPassword entered password
     * @return user if registrated, null if not
     * @throws SQLException
     */
    public static User register(String inputedEmail, String inputedPassword, String confirmedInputedPassword) throws SQLException {
        System.out.println("Proceeding to registration...");

        if (!(DataManipulator.verifyInputedEmail(inputedEmail) | DataManipulator.verifyInputedPassword(inputedPassword) | DataManipulator.verifyConfirmedPassword(inputedPassword, confirmedInputedPassword))) {
            return null;
        }

        System.out.println("You entered " + inputedEmail + ".");
        System.out.println("You entered the password: " + inputedPassword);


        for (User user : (ArrayList<User>) DAOUser.selectAll()) {
            if (user.getId().equalsIgnoreCase(inputedEmail)) {
                System.out.println("User already registered ! Please connect to your account");
                return null; // failed to register
            }
        }

        System.out.println("Proceeding to registration");

        // add the user to the database
        if (DAOUser.insert(new User(inputedEmail, inputedPassword)) != null) {
            System.out.println("user successfully registered!");
            return login(inputedEmail, inputedPassword);;
        }

        // failed to register
        System.out.println("Failed to register the user. Please retry.");
        return null;
    }

    /**
     * OK
     *
     * @param inputedEmail    inputedEmail
     * @param inputedPassword inputedPassword
     * @return user if signed in, null if not
     * @throws SQLException
     */

    public static User login(String inputedEmail, String inputedPassword) throws SQLException {


        if (!(DataManipulator.verifyInputedEmail(inputedEmail) | DataManipulator.verifyInputedPassword(inputedPassword))) {
            return null;
        }

        System.out.println("You entered " + inputedEmail + " .");
        System.out.println("You entered " + inputedPassword + " .");

        User userToLogin = null;
        for (User user : DAOUser.selectAll()) {
            if (user.getId().equalsIgnoreCase(inputedEmail)) {
                userToLogin = user;
                break;
            }
        }

        if (userToLogin == null) {
            System.out.println("Unexisting user, not connected");
            return null;
        }

        if (DAOUser.signIn(userToLogin) != null) {
            System.out.println("Congrates you are now logged in");
            slackSystem.setCurrentConnectedUser(userToLogin);
            return userToLogin;
        }

        System.out.println("Error, you can not log in");
        return null;
    }
}