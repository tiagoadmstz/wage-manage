package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.ContactDetails;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class ContactDetailsRepository implements JpaRepository<ContactDetails, Long> {

    public Optional<ContactDetails> findByEmailAndPhoneNumber(String email, String phoneNumber) {
        return Optional.empty();
    }
}
