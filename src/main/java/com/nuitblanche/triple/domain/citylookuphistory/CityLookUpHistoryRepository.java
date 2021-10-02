package com.nuitblanche.triple.domain.citylookuphistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CityLookUpHistoryRepository extends JpaRepository<CityLookUpHistory, Long> {

}
