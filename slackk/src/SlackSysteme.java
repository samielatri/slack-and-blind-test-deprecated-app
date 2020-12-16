public class SlackSysteme {
    public User connection(String username, String password) {
        for(User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }





    public static void main(String[] args) {
        //toutes les opérations
    }
}
