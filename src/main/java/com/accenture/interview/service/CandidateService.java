package com.accenture.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.repository.CandidateTypeRepository;
import com.accenture.interview.rto.candidate.CandidateTypeRTO;

/**
 * The Class CandidateTypeService.
 */
@Service
public class CandidateService {

	/** The candidate type repository. */
	@Autowired
	private CandidateTypeRepository candidateTypeRepository;

	/**
	 * Gets the candidate type.
	 *
	 * @param description the description
	 * @return the candidate type
	 */
	public CandidateTypeRTO getCandidateType(String description) {
		return candidateTypeRepository.findCandidateTypeByString(description);
	}
}
