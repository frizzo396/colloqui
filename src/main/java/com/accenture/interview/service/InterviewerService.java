package com.accenture.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.repository.InterviewerRepository;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;

/**
 * The Class InterviewerService.
 */
@Service
public class InterviewerService {

	/** The interviewer repository. */
	@Autowired
	private InterviewerRepository interviewerRepository;

	/**
	 * Find interviewer by id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interviewer
	 */
	public InterviewerRTO findInterviewerByEnterpriseId(String enterpriseId) {
		return interviewerRepository.findInterviewerByEnterpriseId(enterpriseId);
	}

	/**
	 * Adds the new interview.
	 *
	 * @param request       the request
	 * @param type the type
	 * @param interviewer   the interviewer
	 * @return the creates the interview response
	 */
	public Interviewer addNewInterviewer(RegisterInterviewerTO request) {
		Interviewer interviewer = new Interviewer(request);
		return interviewerRepository.save(interviewer);
	}	


}
