package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Undress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UndressRepository extends JpaRepository<Undress, Long> {

    Optional<Undress> findByStreetNameAndZipCode(String streetName, String zipCode);

}
