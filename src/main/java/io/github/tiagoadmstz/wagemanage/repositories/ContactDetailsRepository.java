package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {

    Optional<ContactDetails> findByEmailAndPhoneNumber(String email, String phoneNumber);

}
