package com.accenture.interview.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.repository.interviewer.InterviewerRepository;
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
	 * @return the creates the interview response
	 */
	public void addNewInterviewer(RegisterInterviewerTO request) {
		Interviewer interviewer = null;
		Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());
		
		//Update flag
		if(optInterviewer.isPresent()) {
			interviewer = optInterviewer.get();
			interviewer.setType(request.getIsResponsible());
		} else {
			interviewer = new Interviewer(request);
		}
		interviewerRepository.save(interviewer);
	}	
	
	
	/**
	 * Gets the all responsibles.
	 *
	 * @return the all responsibles
	 */
	public List<InterviewerRTO> getAllResponsibles() {
		return interviewerRepository.findAllResponsibles();
	}
	
	/**
	 * Find interviewer by mail.
	 *
	 * @param mail the mail
	 * @return the interviewer RTO
	 */
	public InterviewerRTO findInterviewerByMail(String mail) {
		return interviewerRepository.findInterviewerByMail(mail);
	}
	
	
	/**
	 * Find interviewer by enterprise id or mail.
	 *
	 * @param enterpriseId the enterprise id
	 * @param mail the mail
	 * @return the interviewer RTO
	 */
	public InterviewerRTO findInterviewerByEnterpriseIdOrMail(String enterpriseId, String mail) {
		return interviewerRepository.findInterviewerByEnterpriseIdOrMail(enterpriseId, mail);
	}
	
	/**
	 * Find all interviewers.
	 *
	 * @return the list
	 */
	public List<InterviewerRTO> findAllInterviewers(){		
		return interviewerRepository.findAllInterviewers();
	}
	
	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	public List<InterviewerRTO> findAllUsers(){		
		return interviewerRepository.findAllUsers();
	}	

}
