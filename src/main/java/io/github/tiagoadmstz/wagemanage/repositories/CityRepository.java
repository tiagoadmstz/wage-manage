package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.City;
import jakarta.inject.Named;

import java.util.Optional;

@Named
public class CityRepository implements JpaRepository<City, Long> {

    public Optional<City> findByName(String name) {
        return Optional.empty();
    }
}
