package com.nuitblanche.triple.domain.citylookuphistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CityLookUpHistoryRepository extends JpaRepository<CityLookUpHistory, Long> {

    @Query(value = "SELECT c FROM CityLookUpHistory c LEFT JOIN FETCH c.city WHERE c.user.id=:userId AND cast(c.createdDate as LocalDate) >=:weekAgo AND cast(c.createdDate as LocalDate) <=:now GROUP BY c.city.id HAVING count(c.id) = 1 ORDER BY c.id DESC")
    List<CityLookUpHistory> findByOneLookedUpWithInWeek(@Param("userId") Long userId,@Param("weekAgo")LocalDate weekAgo, @Param("now") LocalDate now);
}
