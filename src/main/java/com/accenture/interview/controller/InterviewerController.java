package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.interviewer.InterviewerRTO;

/**
 * The Class InterviewerController.
 */
@RestController
public class InterviewerController {

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;

	/**
	 * Search interviewer.
	 *
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail             the mail
	 * @return the search interviewer response
	 */
	@GetMapping("/searchInterviewer")
	public InterviewerRTO searchInterviewer(@RequestParam("candidateName") String candidateName,
			@RequestParam("candidateSurname") String candidateSurname,
			@RequestParam("mail") String mail) {
		return interviewerFacade.searchInterviewer(candidateName, candidateSurname, mail);
	}


	/**
	 * Interviewer info.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interviewer
	 */
	@GetMapping("/getUserInfo")
	public InterviewerRTO interviewerInfo(@RequestParam("enterpriseId") String enterpriseId) {
		return interviewerFacade.interviewerInfo(enterpriseId);
	}

}
