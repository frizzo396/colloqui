package com.accenture.interview.repository;

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

/**
 * The Interface InterviewRepository.
 */
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

	/**
	 * Find interview by name surname and mail.
	 *
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail             the mail
	 * @return the optional
	 */
	@Query("select i from Interview i where i.candidateName= :candidate_name and i.candidateSurname= :candidate_surname and i.mail=:mail")
	Optional<Interview> findInterviewByNameSurnameAndMail(@Param("candidate_name") String candidateName,
			@Param("candidate_surname") String candidateSurname, @Param("mail") String mail);

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
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail             the mail
	 * @param interviewType    the interview type
	 * @param firstDate        the first date
	 * @param secondDate       the second date
	 * @param enterpriseId     the enterprise id
	 * @return the list
	 */
	@Query("SELECT new com.accenture.interview.rto.interview.InterviewAndFeedbackRTO(i.id, i.candidateName, "
			+ "i.candidateSurname, "
			+ "ct.description, "
			+ "i.interviewType, "
			+ "i.scheduledDate, "
			+ "s.siteName, "
			+ "i.interviewerId.enterpriseId, "
			+ "i.finalFeedback) FROM Interview i, CandidateType ct, Site s WHERE "
			+ "i.candidateTypeId.id = ct.id and "
			+ "s.id = i.site.id and "
			+ "(i.candidateName = :name OR :name = '') AND "
			+ "(i.candidateSurname = :surname OR :surname = '') AND "
			+ "(i.candidateTypeId.description = :candidateType OR :candidateType = '') AND "
			+ "(i.site.siteName = :site OR :site = '') AND "
			+ "(:intType is null OR i.interviewType = :intType) AND "
			+ "(i.interviewerId.enterpriseId = :entId OR :entId = '') AND "
			+ "(:schedDate is null OR i.scheduledDate>=:schedDate) AND "
			+ "(i.dueDate is null OR :dueDate is null OR i.dueDate<=:dueDate) "
			+ "ORDER BY i.scheduledDate")
	List<InterviewAndFeedbackRTO> findInterviewByParams(@Param("name") String candidateName,
			@Param("surname") String candidateSurname,
			@Param("intType") Long interviewType,
			@Param("schedDate") Date firstDate,
			@Param("dueDate") Date secondDate,
			@Param("entId") String enterpriseId,
			@Param("candidateType") String candidateType,
			@Param("site") String site);

	/**
	 * Gets the my interviews by enterprise id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews by enterprise id
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
			+ "and i.finalFeedback is not null "
			+ "order by i.scheduledDate desc")
	List<InterviewAndFeedbackRTO> getMyInterviewsByEnterpriseId(@Param("enterpriseId") String enterpriseId);

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
			+ "s.siteName) "
			+ "from Interview i, CandidateType ct, Site s "
			+ "where i.interviewerId.enterpriseId=:enterpriseId "
			+ "and i.candidateTypeId.id = ct.id "
			+ "and s.id = i.site.id "
			+ "and i.finalFeedback is null "
			+ "order by i.scheduledDate desc")
	List<InProgressInterviewRTO> getInProgressInterviewsByEnterpriseId(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the in progress interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.finalFeedback is null")
	int getInProgressInterviewsCount(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the my interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.finalFeedback is not null")
	int getMyInterviewsCount(@Param("enterpriseId") String enterpriseId);

	/**
	 * Gets the my interviews month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate    the start date
	 * @param endDate      the end date
	 * @return the my interviews month count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.finalFeedback is not null and (i.dueDate BETWEEN :startDate and :endDate)")
	int getMyInterviewsMonthCount(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * Gets the in progress month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate    the start date
	 * @param endDate      the end date
	 * @return the in progress month count
	 */
	@Query("select count (i) from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.finalFeedback is null and (i.scheduledDate BETWEEN :startDate and :endDate)")
	int getInProgressMonthCount(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * Find year interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @param startDate    the start date
	 * @param endDate      the end date
	 * @return the list
	 */
	@Query("select i from Interview i where i.interviewerId.enterpriseId=:enterpriseId and i.finalFeedback is not null and (i.dueDate BETWEEN :startDate and :endDate)")
	List<Interview> findYearInterviews(@Param("enterpriseId") String enterpriseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
