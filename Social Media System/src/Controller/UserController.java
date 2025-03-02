package Controller;

import DAO.UserDAO;
import Model.User;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public boolean authenticate(String username, String password) {
        User user = userDAO.authenticateUser(username, password);
        return user != null; // Return true if user is found, otherwise false
    }
}
