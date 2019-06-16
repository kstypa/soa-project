package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "soa_addresses")
@Data
@NoArgsConstructor
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String city;
    private String street;
    private String building;
    private int apartment;
    private String postal_code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
