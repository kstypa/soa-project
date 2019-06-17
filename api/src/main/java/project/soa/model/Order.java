package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "soa_orders")
@Data
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDateTime ordered_date;
    private LocalDateTime delivery_date;

    @Enumerated(EnumType.STRING)
    @Basic(fetch = FetchType.EAGER)
    private Status status;

    public enum Status { PLACED, PREPARING, PREPARED, DELIVERING, DELIVERED, CANCELED }

    @ManyToMany
    @JoinTable(
            name = "soa_order_contents",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;
}
