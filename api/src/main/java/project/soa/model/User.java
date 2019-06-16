package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "soa_users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;
    private String password;
    private String first_name;
    private String last_name;
    private Role role;

    public enum Role { MANAGER, CLIENT, COOK, DELIVERY_BOY }
}
