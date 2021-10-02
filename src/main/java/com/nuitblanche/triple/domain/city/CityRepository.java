package com.nuitblanche.triple.domain.city;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {

    Boolean existsByName(String name);

    Optional<City> findByName(String name);

    @Query(value = "SELECT c FROM City c WHERE cast(c.createdDate as LocalDate) >=:weekAgo AND cast(c.createdDate as LocalDate) >=:now  ORDER BY c.createdDate DESC")
    List<City> findRegisteredCitiesWithInOneWeek(@Param("weekAgo") LocalDate weekAgo, @Param("now") LocalDate now);

    @Query(value = "SELECT c FROM City c WHERE c.id NOT IN (:cityIds)")
    List<City> findNotInList(@Param("cityIds") List<Long> cityIds, Pageable pageable);

}
