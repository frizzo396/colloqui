package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.interviewer.SearchInterviewerResponse;

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
	public SearchInterviewerResponse searchInterviewer(@RequestParam("candidateName") String candidateName,
			@RequestParam("candidateSurname") String candidateSurname,
			@RequestParam("mail") String mail) {
		return interviewerFacade.searchInterviewer(candidateName, candidateSurname, mail);
	}

	/**
	 * Account page.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/home")
	public ModelAndView accountPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.setViewName("home.html");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("inProgressInterviews", interviewerFacade.getInProgressInterviewsNumber(username));
		modelAndView.addObject("myInterviews", interviewerFacade.getMyInterviewsNumber(username));
		modelAndView.addObject("myInterviewsMonth", interviewerFacade.getMonthCompletedInterviewsNumber(username));
		modelAndView.addObject("inProgressInterviewsMonth", interviewerFacade.getMonthInProgressInterviewsNumber(username));
		modelAndView.addObject("yearMonthInterviews", interviewerFacade.getYearCompletedInterviews(username));
		return modelAndView;
	}

	/**
	 * Interviewer info.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interviewer
	 */
	@GetMapping("/getUserInfo")
	public Interviewer interviewerInfo(@RequestParam("enterpriseId") String enterpriseId) {
		return interviewerFacade.interviewerInfo(enterpriseId);
	}

}
