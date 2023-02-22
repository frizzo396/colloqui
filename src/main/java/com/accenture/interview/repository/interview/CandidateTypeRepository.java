package com.accenture.interview.repository.interview;

import java.util.List;

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
    * Find candidate type by id.
    *
    * @param id the id
    * @return the candidate type RTO
    */
   @Query("select new com.accenture.interview.rto.candidate.CandidateTypeRTO(c.id, c.description) from CandidateType c where c.id=:id")
   CandidateTypeRTO findCandidateTypeById(@Param("id") Long id);

   /**
    * Find candidate type list.
    *
    * @return the list
    */
   @Query("select new com.accenture.interview.rto.candidate.CandidateTypeRTO(c.id, c.description) from CandidateType c")
   List<CandidateTypeRTO> findCandidateTypeList();

}
