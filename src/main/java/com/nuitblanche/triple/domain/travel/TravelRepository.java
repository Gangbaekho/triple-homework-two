package com.nuitblanche.triple.domain.travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TravelRepository extends JpaRepository<Travel,Long> {

    @Query(value = "SELECT COUNT(t) FROM Travel t where t.user.id=:userId AND t.city.id=:cityId")
    Long countByUserIdAndCityId(@Param("userId") Long userId, @Param("cityId") Long cityId);

    @Query(value = "SELECT t FROM Travel t JOIN FETCH t.city WHERE t.startDate <=:now AND t.endDate >=:now AND t.user.id=:userId ORDER BY t.startDate ASC")
    List<Travel> findTravelNowByUserIdAndLocalDate(@Param("userId") Long userId, @Param("now") LocalDate now);

    @Query(value = "SELECT t FROM Travel t JOIN FETCH t.city WHERE t.startDate >:now AND t.endDate >:now AND t.user.id=:userId ORDER BY t.startDate ASC")
    List<Travel> findTravelFutureByUserIdAndLocalDate(@Param("userId") Long userId, @Param("now") LocalDate now);
}
