package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "undresses")
public class Undress implements Serializable {

    private static final long serialVersionUID = 6460447950685989887L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetName;
    private String zipCode;
    @ManyToOne
    private City city;

    public Undress(String streetName, String zipCode, City city) {
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
    }
}
