package io.github.tiagoadmstz.wagemanage.repositories;

import io.github.tiagoadmstz.wagemanage.entities.WageUser;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
public class WageUserRepository implements JpaRepository<WageUser, Long> {

    public Optional<WageUser> findByUsername(final String username) {
        return Optional.empty();
    }

    @Override
    public List<WageUser> findAll() {
        return new ArrayList<>();
    }

    @Override
    public WageUser save(WageUser save) {
        return save;
    }
}
