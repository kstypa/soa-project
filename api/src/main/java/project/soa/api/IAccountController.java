package project.soa.api;

import project.soa.model.User;

public interface IAccountController {
    public User addUser(String login, String password, String firstName, String lastName);

    public User loginUser(String login, String password);

    public User changePassword(String login, String oldPassword, String newPassword);
}
