import UserManagment.User;

import java.util.ArrayList;

public class SlackSystem {
    private ArrayList<User> users;
    public User connection(String username, String password) {
        for(User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }
}
