package project.soa.controller;

import project.soa.api.IUserController;
import project.soa.model.Dish;
import project.soa.model.Order;
import project.soa.model.User;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AbstractController implements IUserController {
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_users", User.class);

        try {
            users = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return users;
    }

    @Override
    public List<User> getUsersByRole(User.Role role) {
        List<User> users = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_users where role=:role", User.class);
        query.setParameter("role", role);

        try {
            users = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return users;
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUser(String login) {
        Query query = entityManager.createQuery("from soa_users where login = :login", User.class);
        query = query.setParameter("login", login);

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
    public User addUser(String login, String password, String first_name, String last_name, User.Role role) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirst_name(first_name);
        user.setLast_name(last_name);
        user.setRole(role);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return user;
    }

    @Override
    public User editUser(User user, String login, String password, String first_name, String last_name, User.Role role) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(user);
            user.setLogin(login);
            user.setPassword(password);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setRole(role);
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return user;
    }

    @Override
    public void deleteUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
}

