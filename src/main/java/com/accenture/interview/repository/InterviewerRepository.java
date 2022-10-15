package com.accenture.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.rto.interviewer.InterviewerRTO;

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
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail) from Interviewer ier where ier.id=:interviewerId")
	InterviewerRTO findInterviewerByInterviewerId(@Param("interviewerId") long interviewerId);

	/**
	 * Find interviewer by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the optional
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail) from Interviewer ier where ier.enterpriseId=:enterpriseId")
	InterviewerRTO findInterviewerByEnterpriseId(@Param("enterpriseId") String enterpriseId);

}
