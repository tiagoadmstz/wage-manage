package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "undresses")
public class Undress implements Serializable {

    private static final long serialVersionUID = 6460447950685989887L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetName;
    private String zipCode;
    @ManyToOne(cascade = CascadeType.ALL)
    private City city;

}
