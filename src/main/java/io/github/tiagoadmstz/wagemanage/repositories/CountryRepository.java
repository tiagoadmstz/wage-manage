package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Country;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class CountryRepository implements JpaRepository<Country, Long> {
    public Optional<Country> findByName(String name) {
        return Optional.empty();
    }
}
