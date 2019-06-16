package project.soa.api;

import project.soa.model.Address;
import project.soa.model.User;

import java.util.List;

public interface IAddressController {

    public List<Address> getAllAddresses();

    public List<Address> getAddressesByUser(User user);

    public Address getAddress(int id);

    public Address addAddress(String city, String street, String building, int apartment, String postal_code, User user);

    public Address editAddress(Address address, String city, String street, String building, int apartment, String postal_code, User user);

    public void deleteAddress(Address address);
}
