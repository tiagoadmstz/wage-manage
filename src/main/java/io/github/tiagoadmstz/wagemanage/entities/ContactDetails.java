package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "contact_details")
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = -4904587046662783144L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phoneNumber;

}
