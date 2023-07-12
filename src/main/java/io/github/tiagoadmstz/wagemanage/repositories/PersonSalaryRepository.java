package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.PersonSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonSalaryRepository extends JpaRepository<PersonSalary, Long> {
}
