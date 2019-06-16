package project.soa.controller;

import project.soa.api.IAddressController;
import project.soa.model.Address;
import project.soa.model.User;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AddressController extends AbstractController implements IAddressController {
    @Override
    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_addresses", Address.class);

        try {
            addresses = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return addresses;
    }

    @Override
    public List<Address> getAddressesByUser(User user) {
        List<Address> addresses = new ArrayList<>();
        Query query = entityManager.createQuery("from soa_addresses where user = :user", Address.class);
        query.setParameter("user", user);

        try {
            addresses = query.getResultList();
        }
        catch (Exception e) {
            System.out.println("select error");
        }

        return addresses;
    }

    @Override
    public Address getAddress(int id) {
        return entityManager.find(Address.class, id);
    }

    @Override
    public Address addAddress(String city, String street, String building, int apartment, String postal_code, User user) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setBuilding(building);
        address.setApartment(apartment);
        address.setPostal_code(postal_code);
        address.setUser(user);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(address);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error adding");
        }

        return address;
    }

    @Override
    public Address editAddress(Address address, String city, String street, String building, int apartment, String postal_code, User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.detach(address);
            address.setCity(city);
            address.setStreet(street);
            address.setBuilding(building);
            address.setApartment(apartment);
            address.setPostal_code(postal_code);
            address.setUser(user);
            entityManager.merge(address);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error editing");
        }

        return address;
    }

    @Override
    public void deleteAddress(Address address) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(address);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error deleting");
        }
    }
}
