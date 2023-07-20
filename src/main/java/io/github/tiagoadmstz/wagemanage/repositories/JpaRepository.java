package io.github.tiagoadmstz.wagemanage.repositories;

import java.util.ArrayList;
import java.util.List;

public interface JpaRepository<T, ID> {

    default List<T> findAll() {
        return new ArrayList<>();
    }

    default T save(T save) {
        return (T) new Object();
    }
}
