package com.accenture.interview.repository.interview;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Availability;

/**
 * The Interface AvailabilityRepository.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
	
	/**
	 * Find availability dates by interview id.
	 *
	 * @param interviewId the interview id
	 * @return the list
	 */
	@Query("SELECT a.availabileDate FROM Availability a where a.interview.id = :interviewId")
	List<Date> findAvailabilityDatesByInterviewId(@Param("interviewId") Long interviewId);

   /**
    * Find availability by interview id.
    *
    * @param interviewId the interview id
    * @return the list
    */
   @Query("SELECT a FROM Availability a where a.interview.id = :interviewId")
   List<Availability> findAvailabilityByInterviewId(@Param("interviewId") Long interviewId);

}