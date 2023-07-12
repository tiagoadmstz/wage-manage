package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -1751442833579385644L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal salary;

}
