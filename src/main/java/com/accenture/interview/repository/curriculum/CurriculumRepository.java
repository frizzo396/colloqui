package com.accenture.interview.repository.curriculum;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Curriculum;

/**
 * The Interface CurriculumRepository.
 */
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
	
	
	/**
	 * Find curriculum by interview id.
	 *
	 * @param interviewId the interview id
	 * @return the curriculum
	 */
	@Query("SELECT c FROM Curriculum c WHERE c.interviewId = :interviewId")
	Optional<Curriculum> findCurriculumByInterviewId(@Param("interviewId")Long interviewId);

}
