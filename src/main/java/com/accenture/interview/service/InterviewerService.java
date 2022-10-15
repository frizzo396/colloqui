package com.accenture.interview.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.repository.InterviewRepository;
import com.accenture.interview.repository.InterviewerRepository;
import com.accenture.interview.rto.interviewer.InterviewerRTO;

/**
 * The Class InterviewerService.
 */
@Service
public class InterviewerService {

	/** The interviewer repository. */
	@Autowired
	private InterviewerRepository interviewerRepository;

	/** The interview repository. */
	@Autowired
	private InterviewRepository interviewRepository;

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
	 * Find interview by name surname and mail.
	 *
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail             the mail
	 * @return the interview
	 */
	public Interview findInterviewByNameSurnameAndMail(String candidateName, String candidateSurname, String mail) {
		Optional<Interview> opt = interviewRepository.findInterviewByNameSurnameAndMail(candidateName, candidateSurname, mail);

		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

}
