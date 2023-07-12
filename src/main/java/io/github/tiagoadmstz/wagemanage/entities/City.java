package io.github.tiagoadmstz.wagemanage.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cities")
public class City implements Serializable {

    private static final long serialVersionUID = -988117869153412446L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;

}
