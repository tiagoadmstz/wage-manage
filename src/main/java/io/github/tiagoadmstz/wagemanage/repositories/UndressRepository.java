package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Undress;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
public class UndressRepository implements JpaRepository<Undress, Long> {

    public Optional<Undress> findByStreetNameAndZipCode(String streetName, String zipCode) {
        return Optional.empty();
    }

    @Override
    public List<Undress> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Undress save(Undress save) {
        return save;
    }
}
