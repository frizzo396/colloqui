package com.accenture.interview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.repository.interview.CandidateTypeRepository;
import com.accenture.interview.rto.candidate.CandidateTypeRTO;

/**
 * The Class CandidateService.
 */
@Service
public class CandidateService {

	/** The candidate type repository. */
	@Autowired
	private CandidateTypeRepository candidateTypeRepository;

	/**
    * Gets the candidate type.
    *
    * @param id the id
    * @return the candidate type
    */
   public CandidateTypeRTO getCandidateType(Long id) {
      return candidateTypeRepository.findCandidateTypeById(id);
	}

   /**
    * Gets the candidate type list.
    *
    * @return the candidate type list
    */
   public List<CandidateTypeRTO> getCandidateTypeList() {
      return candidateTypeRepository.findCandidateTypeList();
   }
}
