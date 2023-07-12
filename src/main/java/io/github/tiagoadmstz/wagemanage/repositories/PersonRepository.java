package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
