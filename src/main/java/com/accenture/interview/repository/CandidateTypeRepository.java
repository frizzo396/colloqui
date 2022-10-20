package com.accenture.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.CandidateType;
import com.accenture.interview.rto.candidate.CandidateTypeRTO;

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
	@Query("select new com.accenture.interview.rto.candidate.CandidateTypeRTO(c.id, c.description) from CandidateType c where c.description=:description")
	CandidateTypeRTO findCandidateTypeByString(@Param("description") String description);

}
