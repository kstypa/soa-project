package project.soa.api;

import project.soa.model.User;

import java.util.List;

public interface IUserController {

    public List<User> getAllUsers();

    public List<User> getUsersByRole(User.Role role);

    public User getUser(int id);

    public User getUser(String login);

    public User addUser(String login, String password, String first_name, String last_name, User.Role role);

    public User editUser(User user, String login, String password, String first_name, String last_name, User.Role role);

    public User editUserRole(User user, User.Role role);

    public void deleteUser(User user);
}
