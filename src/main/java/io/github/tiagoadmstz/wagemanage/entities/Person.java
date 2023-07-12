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
    @OneToOne(cascade = CascadeType.ALL)
    private Undress undress;
    @OneToOne(cascade = CascadeType.ALL)
    private ContactDetails contactDetails;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private WageUser user;
}
