package io.github.tiagoadmstz.wagemanage.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "wage_users")
public class WageUser implements Serializable {

    private static final long serialVersionUID = -5109687024902908517L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public WageUser(String username) {
        this.username = username;
    }
}
