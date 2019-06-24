package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "soa_subscriptions")
@Data
@NoArgsConstructor
public class Subscription implements Serializable {

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

    private LocalTime delivery_hour;

    @Enumerated(EnumType.STRING)
    @Basic(fetch = FetchType.EAGER)
    private DayOfWeek delivery_day;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "soa_subscription_contents",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;
}
