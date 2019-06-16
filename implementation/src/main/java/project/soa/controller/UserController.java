package project.soa.controller;

import project.soa.api.IUserController;
import project.soa.model.User;

import java.util.List;

public class UserController extends AbstractController implements IUserController {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getUsersByRole(User.Role role) {
        return null;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUser(String login) {
        return null;
    }

    @Override
    public User addUser(String login, String password, String first_name, String last_name, User.Role role) {
        return null;
    }

    @Override
    public User editUser(User user, String login, String password, String first_name, String last_name, User.Role role) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }
}
