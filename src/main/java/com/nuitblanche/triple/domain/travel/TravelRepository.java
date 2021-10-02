package com.nuitblanche.triple.domain.travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelRepository extends JpaRepository<Travel,Long> {

    @Query(value = "SELECT COUNT(t) FROM Travel t where t.user.id=:userId AND t.city.id=:cityId")
    Long countByUserIdAndCityId(@Param("userId") Long userId, @Param("cityId") Long cityId);
}
