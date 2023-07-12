package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
