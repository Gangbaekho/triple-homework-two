package com.nuitblanche.triple.domain.citylookuphistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CityLookUpHistoryRepository extends JpaRepository<CityLookUpHistory, Long> {


    @Query(value = "SELECT clh.city.id FROM CityLookUpHistory clh WHERE clh.user.id=:userId AND cast(clh.createdDate as LocalDate)>=:weekAgo AND cast(clh.createdDate as LocalDate)<=:now GROUP BY clh.city.id HAVING COUNT(clh.id)=1")
    List<Long> test(@Param("userId") Long userId,@Param("weekAgo")LocalDate weekAgo, @Param("now") LocalDate now);
}
