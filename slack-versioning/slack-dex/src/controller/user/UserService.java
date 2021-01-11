package controller.user;

import database.DAO;
import database.DAOFactory;
import model.SlackSystem;
import model.user.User;
import tool.DataManipulator;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * UserServiceDAO : provide services related to the User
 */
public class UserService {

    private DAO<User> DAOUser;
    private SlackSystem slackSystem;

    public UserService(SlackSystem slackSystem) throws SQLException {
        DAOUser=DAOFactory.user();
        this.slackSystem=slackSystem;
    }


    /**
     * OK
     *
     * @param inputedEmail             entered email
     * @param inputedPassword          entered password
     * @param confirmedInputedPassword entered password
     * @return user if registrated, null if not
     * @throws SQLException
     */
    public User register(String inputedEmail, String inputedPassword, String confirmedInputedPassword) throws SQLException {
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
            return login(inputedEmail, inputedPassword);
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

    public User login(String inputedEmail, String inputedPassword) throws SQLException {


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
            slackSystem.setCurrentConnectedUser(new User(userToLogin));
            return userToLogin;
        }

        System.out.println("Error, you can not log in");
        return null;
    }


    /************************************************************************

    public void editAccount(){
        String newEmailAddress = "";
        String newPasswordConfirm = "";
        String newPassword = "";
        boolean passwordConfirmed = false;
        String currentPassword = "";
        User currentUser = getConnectedUser();

        System.out.println("Edit your account");

        if (currentUser == null) {
            System.out.println("No user connected !");
            return ;
        }

        int intInput = 0;
        do {
            System.out.println("edit :\n" + "\t1- email address\n\t2- password\n\t 3-return");
            intInput = readInt("your choice");
        } while (intInput > 0 && intInput < 3);
        if (intInput == 1){
            do {
                newEmailAddress = readString("new email address");
                currentUser.setEmail(newEmailAddress);
                System.out.println("Email Address changed successfully");
            }while(! isValidEmailAddress(newEmailAddress));
        }
        if (intInput== 2) {
            currentPassword = readString("current password");
            if (currentPassword == currentUser.getPassword()) {
                do {
                    newPassword = readString("new password");
                    newPasswordConfirm = readString("new password confirmation");
                    passwordConfirmed = (newPassword == newPasswordConfirm);
                    if (passwordConfirmed) {
                        currentUser.setPassword(newPassword);
                        System.out.println("Password changed successfully");
                    }
                } while (!passwordConfirmed);
            } else {
                System.out.println("Password does not match");
                // return
            }
        }
        // 3
        // return
    }

    public void disconnect(){
        System.out.println("disconnect");
        if (slackSystem.getConnectedUser() == null){
            System.out.println("no connected user");
            return;
        }
        System.out.println("disconnecting...");
        slackSystem.setConnectedUser(null);
        System.out.println("disconnected successfully");
    }

    public void deleteAccount(){
        System.out.println("delete account");
        User user = slackSystem.getConnectedUser();
        if (user == null){
            System.out.println("no user connected");
            return;
        }
        disconnect();
        System.out.println("Deleting account...");
        ArrayList<User> usersList = slackSystem.getUsers();
        if(usersList.contains(user)) {
            usersList.remove(user);
            slackSystem.setUsers(usersList);
            System.out.println("Account deleted");
        } else {
            System.out.println("The user was never registred... /!\\");
        }
    }


    *********************************************************/






}