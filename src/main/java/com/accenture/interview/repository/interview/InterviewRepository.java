package com.accenture.interview.repository.interview;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewRTO;

/**
 * The Interface InterviewRepository.
 */
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

	/**
	 * Find interview by name surname and mail.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail the mail
	 * @return the optional
	 */
	@Query("select i from Interview i where i.candidateName= :candidate_name and i.candidateSurname= :candidate_surname and i.mail=:mail")
	Optional<Interview> findInterviewByNameSurnameAndMail(@Param("candidate_name") String candidateName,
			@Param("candidate_surname") String candidateSurname, @Param("mail") String mail);

	/**
	 * Find interview by mail.
	 *
	 * @param mail the mail
	 * @return the optional
	 */
	@Query("select i from Interview i where i.mail=:mail")
	Optional<Interview> findInterviewByMail(@Param("mail") String mail);

	/**
	 * Find interview by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Query("select i from Interview i where i.id=:id")
	Optional<Interview> findInterviewById(@Param("id") long id);

	/**
	 * Find interview by params.
	 *
	 * @param candidateName the candidate name
	 * @param candidateSurname the candidate surname
	 * @param interviewType the interview type
	 * @param firstDate the first date
	 * @param secondDate the second date
	 * @param enterpriseId the enterprise id
	 * @param candidateType the candidate type
	 * @param site the site
	 * @return the list
	 */
	@Query("SELECT new com.accenture.interview.rto.interview.InterviewAndFeedbackRTO(i.id, i.candidateName, "
			+ "i.candidateSurname, "
			+ "ct.description, "
			+ "i.interviewType, "
			+ "i.scheduledDate, "
			+ "s.siteName, "
			+ "i.interviewerId.enterpriseId, "
			+ "i.finalFeedback, "
         + "i.status, "
         + "i.curriculumId) FROM Interview i, CandidateType ct, Site s WHERE "
			+ "i.candidateTypeId.id = ct.id and "
			+ "s.id = i.site.id and "
         + "(i.candidateName LIKE CONCAT('%',:name,'%') OR :name = '') AND "
         + "(i.candidateSurname LIKE CONCAT('%',:surname,'%') OR :surname = '') AND "
			+ "(i.candidateTypeId.description = :candidateType OR :candidateType = '') AND "
			+ "(i.site.siteName = :site OR :site = '') AND "
			+ "(:intType is null OR i.interviewType = :intType) AND "
			+ "(i.interviewerId.enterpriseId = :entId OR :entId = '') AND "
			+ "(:schedDate is null OR i.scheduledDate>=:schedDate) AND "
			+ "(i.dueDate is null OR :dueDate is null OR i.dueDate<=:dueDate) "
         + "ORDER BY i.updatedDate desc")
	List<InterviewAndFeedbackRTO> findInterviewByParams(@Param("name") String candidateName,
			@Param("surname") String candidateSurname,
			@Param("intType") Long interviewType,
			@Param("schedDate") Date firstDate,
			@Param("dueDate") Date secondDate,
			@Param("entId") String enterpriseId,
			@Param("candidateType") String candidateType,
			@Param("site") String site);

   /**
    * Find interview by params.
    *
    * @param candidateName the candidate name
    * @param interviewType the interview type
    * @param enterpriseId  the enterprise id
    * @param candidateType the candidate type
    * @param site          the site
    * @param status        the status
    * @return the list
    */
   @Query("SELECT new com.accenture.interview.rto.interview.InterviewAndFeedbackRTO(i.id, i.candidateName, "
	         + "i.candidateSurname, "
	         + "ct.description, "
	         + "i.interviewType, "
	         + "i.scheduledDate, "
	         + "s.siteName, "
	         + "i.interviewerId.enterpriseId, "
	         + "i.finalFeedback, "
         + "i.status, "
         + "i.curriculumId) FROM Interview i, CandidateType ct, Site s WHERE "
	         + "i.candidateTypeId.id = ct.id and "
	         + "s.id = i.site.id and "
         + "(i.candidateName LIKE CONCAT('%',:candidateName,'%') OR i.candidateSurname LIKE CONCAT('%',:candidateName,'%') OR :candidateName = '') AND "
	         + "(i.candidateTypeId.description = :candidateType OR :candidateType = '') AND "
	         + "(i.site.siteName = :site OR :site = '') AND "
	         + "(:intType is null OR i.interviewType = :intType) AND "
         + "(i.status = :status OR :status is null) AND "
         + "(i.interviewerId.enterpriseId = :entId OR :entId = '') AND "
         + "(i.finalFeedback = :feedback OR :feedback = '') "
         + "ORDER BY i.updatedDate desc")
	   List<InterviewAndFeedbackRTO> findInterviewByParams(@Param("candidateName") String candidateName,
	         @Param("intType") Long interviewType,
	         @Param("entId") String enterpriseId,
	         @Param("candidateType") String candidateType,
            @Param("site") String site,
            @Param("status") Integer status,
            @Param("feedback") String feedback);

	/**
	 * Find assigned interviews.
	 *
	 * @return the list
	 */
	@Query("SELECT new com.accenture.interview.rto.interview.InterviewAndFeedbackRTO(i.id, i.candidateName, "
			+ "i.candidateSurname, "
			+ "ct.description, "
			+ "i.interviewType, "
			+ "i.scheduledDate, "
			+ "s.siteName, "
			+ "i.interviewerId.enterpriseId, "
			+ "i.finalFeedback, i.status) FROM Interview i, CandidateType ct, Site s WHERE "
			+ "i.candidateTypeId.id = ct.id and "
         + "s.id = i.site.id "
         + "ORDER BY i.updatedDate desc")
   List<InterviewAndFeedbackRTO> findAssignedInterviews();

	/**
	 * Find completed interviews by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the list
	 */
	@Query("select new com.accenture.interview.rto.interview.InterviewAndFeedbackRTO(i.id, i.candidateName, "
			+ "i.candidateSurname, "
			+ "ct.description, "
			+ "i.interviewType, "
			+ "i.scheduledDate, "
			+ "s.siteName, "
			+ "i.finalFeedback)" 
			+ "from Interview i, CandidateType ct, Site s "
			+ "where i.candidateTypeId.id = ct.id "
			+ "and i.interviewerId.enterpriseId=:enterpriseId "
			+ "and s.id = i.site.id "
			+ "and i.status = 4 "
         + "ORDER BY i.updatedDate desc")
	List<InterviewAndFeedbackRTO> findCompletedInterviewsByEnterpriseId(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the in progress interviews by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews by enterprise id
	 */	
	@Query("select new com.accenture.interview.rto.interview.InProgressInterviewRTO"
			+ "(i.id, "
			+ "i.candidateName, "
			+ "i.candidateSurname, "
			+ "ct.description, "
			+ "i.interviewType, "
			+ "i.scheduledDate, "
			+ "s.siteName, "
			+ "i.status,"
			+ "i.curriculumId, i.note) " /* 2023-01-17 AGGIUNTO CAMPO NOTE */
			+ "from Interview i, CandidateType ct, Site s "
			+ "where i.interviewerId.enterpriseId=:enterpriseId "
			+ "and i.candidateTypeId.id = ct.id "
			+ "and s.id = i.site.id "
         + "and i.status in (1, 2, 3, 6) "
         + "ORDER BY i.updatedDate desc")
	List<InProgressInterviewRTO> getInProgressInterviewsByEnterpriseId(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the in progress interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.status IN (1, 2, 3)")
	int getInProgressInterviewsCount(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the completed interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the completed interviews count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.status = 4")
	int getCompletedInterviewsCount(@Param("enterpriseId") String enterpriseId);

	/**
	 * Find completed interviews month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the int
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.status = 4 and (i.dueDate BETWEEN :startDate and :endDate)")
	int findCompletedInterviewsMonthCount(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * Gets the in progress month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the in progress month count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.status IN (1, 2, 3) and (i.scheduledDate BETWEEN :startDate and :endDate)")
	int getInProgressMonthCount(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * Find year interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 */
	@Query("select i from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.status = 4 and (i.dueDate BETWEEN :startDate and :endDate)")
	List<Interview> findYearInterviews(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


	/**
	 * Find interview with mail params.
	 *
	 * @param interviewId the interview id
	 * @return the interview RTO
	 */
	@Query("SELECT new com.accenture.interview.rto.interview.InterviewRTO(i.id, s.siteName, i.candidateName, "
			+ "i.candidateSurname, "
			+ "i.mail, "
			+ "i.interviewerId.mail, "
         + "ass.mail) "
			+ "FROM Interview i, Interviewer ass, Site s WHERE "
			+ "s.id = i.site.id and "
			+ "ass.id = i.assigner and "
			+ "i.id = :interviewId")
	InterviewRTO findInterviewWithMailParams(@Param("interviewId") Long interviewId);
	
   /**
    * Find interview to reassign.
    *
    * @param interviewId the interview id
    * @return the interview RTO
    */
   @Query("SELECT new com.accenture.interview.rto.interview.InterviewRTO(i.id, s.siteName, i.candidateName, "
         + "i.candidateSurname, "
         + "i.mail, "
         + "i.interviewerId.mail, "
         + "ass.mail, "
         + "i.note) "
         + "FROM Interview i, Interviewer ass, Site s WHERE "
         + "s.id = i.site.id and "
         + "ass.id = i.assigner and "
         + "i.id = :interviewId")
   InterviewRTO findInterviewToReassign(@Param("interviewId") Long interviewId);

	/**
	 * Find last interview by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interview
	 */
	@Query("select i from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.id = (SELECT MAX(t.id) FROM Interview t)")
	Interview findLastInterviewByEnterpriseId(@Param("enterpriseId") String enterpriseId);


}
