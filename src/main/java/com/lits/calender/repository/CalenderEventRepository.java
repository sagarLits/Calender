package com.lits.calender.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lits.calender.entity.CalenderEvent;

@Repository
public interface CalenderEventRepository extends JpaRepository<CalenderEvent, String>{	 
//	@Query("SELECT e FROM CalenderEvent e WHERE e.eventDate BETWEEN :startDate AND :endDate")
//	List<CalenderEvent> findByMonthAndYear(@Param("startDate") LocalDate startDate,
//	                                       @Param("endDate") LocalDate endDate);
	
	
	  // Normal events (non-recurring, match year+month)
    @Query("SELECT e FROM CalenderEvent e WHERE e.eventDate BETWEEN :start AND :end AND e.recurring = false")
    List<CalenderEvent> findByMonthAndYear(@Param("start") LocalDate start, @Param("end") LocalDate end);

    // Recurring events (ignore year, only month)
    @Query("SELECT e FROM CalenderEvent e WHERE FUNCTION('MONTH', e.eventDate) = :month AND e.recurring = true")
    List<CalenderEvent> findRecurringEventsByMonth(@Param("month") int month);

}
