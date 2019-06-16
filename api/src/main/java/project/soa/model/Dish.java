package project.soa.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "soa_dishes")
@Data
@NoArgsConstructor
public class Dish implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String size;
    private boolean approved;
    private boolean archived;
    private boolean today;
    private int times_ordered;

}
