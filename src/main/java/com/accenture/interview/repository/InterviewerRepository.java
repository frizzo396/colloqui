package com.accenture.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Interviewer;

/**
 * The Interface InterviewerRepository.
 */
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

	/**
	 * Find interviewer by interviewer id.
	 *
	 * @param interviewerId the interviewer id
	 * @return the optional
	 */
	@Query("select ier from Interviewer ier where ier.id=:interviewerId")
	Optional<Interviewer> findInterviewerByInterviewerId(@Param("interviewerId") long interviewerId);

	/**
	 * Find interviewer by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the optional
	 */
	@Query("select ier from Interviewer ier where ier.enterpriseId=:enterpriseId")
	Optional<Interviewer> findInterviewerByEnterpriseId(@Param("enterpriseId") String enterpriseId);

}
