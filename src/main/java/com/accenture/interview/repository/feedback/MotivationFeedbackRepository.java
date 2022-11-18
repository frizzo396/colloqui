package com.accenture.interview.repository.feedback;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;

/**
 * The Interface MotivationFeedbackRepository.
 */
@Repository
public interface MotivationFeedbackRepository extends JpaRepository<MotivationFeedback, Long> {

	/**
	 * Gets the motivation feedback by id interview.
	 *
	 * @param idColloquio the id colloquio
	 * @return the motivation feedback by id interview
	 */
	@Query("select mf from Motivation_feedback mf where mf.interview.id=:idColloquio")
	Optional<MotivationFeedback> getMotivationFeedbackByIdInterview(@Param("idColloquio") Long idColloquio);

	/**
	 * Gets the motivation feedback RTO by id interview.
	 *
	 * @param idColloquio the id colloquio
	 * @return the motivation feedback RTO by id interview
	 */
	@Query("select new com.accenture.interview.rto.feedback.MotivationalFeedbackRTO(i.standing, i.schoolBackground, i.motivation, i.softSkills,i.englishLevel, i.logicQuestion, i.techQuestion, i.comment)"
			+ " from Motivation_feedback i where i.interview.id=:idColloquio")
	MotivationalFeedbackRTO getMotivationFeedbackRTOByIdInterview(@Param("idColloquio") Long idColloquio);

}
