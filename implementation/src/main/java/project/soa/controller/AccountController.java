package project.soa.controller;

import com.google.common.hash.Hashing;
import project.soa.api.IAccountController;
import project.soa.model.User;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;

public class AccountController extends AbstractController implements IAccountController {
    @Override
    public User addUser(String login, String password, String firstName, String lastName) {
        if (loginUser(login, password) != null) {
            return null;
        }
        else {
            String shaPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            User user = new User();
            user.setLogin(login);
            user.setPassword(shaPassword);
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.setRole(User.Role.CLIENT);

            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();

            return user;
        }
    }

    @Override
    public User loginUser(String login, String password) {
        Query query = entityManager.createQuery("from soa_users where login = :login and password = :password", User.class);

        String shaPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        query.setParameter("login", login);
        query.setParameter("password", shaPassword);

        try {
            return (User) query.getSingleResult();
        }
        catch (NonUniqueResultException e) {
            System.out.println("non unique " + login);
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public User changePassword(String login, String oldPassword, String newPassword) {
        User user = loginUser(login, oldPassword);
        if (user == null) {
            return null;
        }
        else {
            String shaPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
            entityManager.getTransaction().begin();
            user.setPassword(shaPassword);
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return user;
        }
    }
}
