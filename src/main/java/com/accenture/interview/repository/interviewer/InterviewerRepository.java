package com.accenture.interview.repository.interviewer;

import java.util.List;
import java.util.Optional;

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
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type) from Interviewer ier where ier.id=:interviewerId")
	InterviewerRTO findInterviewerByInterviewerId(@Param("interviewerId") long interviewerId);
	

	/**
	 * Find interviewer entity by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the optional
	 */
	@Query("select ier from Interviewer ier where ier.enterpriseId=:enterpriseId")
	Optional<Interviewer> findInterviewerEntityByEnterpriseId(@Param("enterpriseId") String enterpriseId);
	
	
	/**
	 * Find interviewer entity by id (primary key).
	 *
	 * @param id the id (primary key)
	 * @return the optional
	 */
	@Query("select ier from Interviewer ier where ier.id=:Id")
	Optional<Interviewer> findInterviewerEntityById(@Param("Id") long id);	

	/**
	 * Find interviewer by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the optional
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.password, ier.type) from Interviewer ier where ier.enterpriseId=:enterpriseId")
	InterviewerRTO findInterviewerByEnterpriseId(@Param("enterpriseId") String enterpriseId);
		
	/**
	 * Find interviewer by enterprise id and by email (for recover password).
	 *
	 * @param enterpriseId the enterprise id
	 * @param mail the email
	 * @return the optional
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.password, ier.type) "
			+ "from Interviewer ier where ier.enterpriseId=:enterpriseId and ier.mail=:mail and ier.status=1")
	InterviewerRTO findInterviewerByEnterpriseIdAndMail(@Param("enterpriseId") String enterpriseId, @Param("mail") String email);	
		
	/**
	 * Find all responsibles.
	 *
	 * @return the list
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type) from Interviewer ier where ier.type= 1")
	List<InterviewerRTO> findAllResponsibles();
	
	
	/**
	 * Find interviewer by mail.
	 *
	 * @param mail the mail
	 * @return the interviewer RTO
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type) from Interviewer ier where ier.mail=:mail")
	InterviewerRTO findInterviewerByMail(@Param("mail") String mail);
	
	/**
	 * Find interviewer by enterprise id or mail.
	 *
	 * @param enterpriseId the enterprise id
	 * @param mail the mail
	 * @return the interviewer RTO
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type) from Interviewer ier where (ier.enterpriseId =:enterpriseId OR ier.mail=:mail)")
	InterviewerRTO findInterviewerByEnterpriseIdOrMail(@Param("enterpriseId") String enterpriseId, @Param("mail") String mail);
	
	
	/**
	 * Find all interviewers.
	 *
	 * @return the list
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type) from Interviewer ier where ier.status = 1")
	List<InterviewerRTO> findAllInterviewers();
	
	/**
	 * Find all interviewers.
	 *
	 * @return the list
	 */
	@Query("select new com.accenture.interview.rto.interviewer.InterviewerRTO(ier.id, ier.enterpriseId, ier.mail, ier.type, ier.status) from Interviewer ier")
	List<InterviewerRTO> findAllUsers();	
}
