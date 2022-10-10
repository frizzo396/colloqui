package com.accenture.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;

/**
 * The Interface TechFeedbackRepository.
 */
@Repository
public interface TechFeedbackRepository extends JpaRepository<TechFeedback, Long> {

	/**
	 * Gets the tech feedback by id interview.
	 *
	 * @param idColloquio the id colloquio
	 * @return the tech feedback by id interview
	 */
	@Query("select tf from Tech_feedback tf where tf.interview.id=:idColloquio")
	Optional<TechFeedback> getTechFeedbackByIdInterview(@Param("idColloquio") Long idColloquio);

	/**
	 * Gets the tech feedback RTO by id interview.
	 *
	 * @param idColloquio the id colloquio
	 * @return the tech feedback RTO by id interview
	 */
	@Query("select new com.accenture.interview.rto.feedback.TechnicalFeedbackRTO(i.java, i.sql, i.htmlCss, i.angular, i.spring, i.other, i.comment)" +
			" from Tech_feedback i where i.interview.id=:idColloquio")
	TechnicalFeedbackRTO getTechFeedbackRTOByIdInterview(@Param("idColloquio") Long idColloquio);
}
