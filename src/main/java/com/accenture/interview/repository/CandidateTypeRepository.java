package com.accenture.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.CandidateType;

/**
 * The Interface CandidateTypeRepository.
 */
@Repository
public interface CandidateTypeRepository extends JpaRepository<CandidateType, Long> {

	/**
	 * Find candidate type by string.
	 *
	 * @param description the description
	 * @return the optional
	 */
	@Query("select c from Candidate_type c where c.description=:description")
	Optional<CandidateType> findCandidateTypeByString(@Param("description") String description);

}
