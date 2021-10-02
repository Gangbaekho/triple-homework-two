package com.nuitblanche.triple.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {

    Boolean existsByName(String name);

    Optional<City> findByName(String name);
}
