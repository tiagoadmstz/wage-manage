package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "persons")
public class Person implements Serializable {

    private static final long serialVersionUID = -6817341158296832619L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
    @OneToOne
    private Undress undress;
    @OneToOne
    private ContactDetails contactDetails;
    @OneToOne
    private Role role;
    @OneToOne
    private WageUser user;
}
