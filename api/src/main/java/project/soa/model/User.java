package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "soa_users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;
    private String password;
    private String first_name;
    private String last_name;

    @Enumerated(EnumType.STRING)
    @Basic(fetch = FetchType.EAGER)
    private Role role;

    public enum Role { MANAGER, CLIENT, COOK, DELIVERY_BOY }
}
