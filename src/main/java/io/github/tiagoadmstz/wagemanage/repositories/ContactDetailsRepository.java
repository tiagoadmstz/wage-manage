package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
}
