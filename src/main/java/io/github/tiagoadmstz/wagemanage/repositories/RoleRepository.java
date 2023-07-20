package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Role;
import jakarta.inject.Named;

@Named
public class RoleRepository implements JpaRepository<Role, Long> {
}
