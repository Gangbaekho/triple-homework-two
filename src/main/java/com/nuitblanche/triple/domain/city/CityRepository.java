package com.nuitblanche.triple.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {

    Boolean existsByName(String name);
}
