package io.github.tiagoadmstz.wagemanage.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City implements Serializable {

    private static final long serialVersionUID = -988117869153412446L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}
