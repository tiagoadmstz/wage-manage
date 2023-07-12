package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.WageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageUserRepository extends JpaRepository<WageUser, Long> {
}
