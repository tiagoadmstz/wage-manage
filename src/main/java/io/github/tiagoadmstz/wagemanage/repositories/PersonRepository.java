package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.Person;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named
public class PersonRepository implements JpaRepository<Person, Long> {
    @Override
    public List<Person> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Person save(Person save) {
        return save;
    }
}
