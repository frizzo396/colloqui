package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.annotation.Registered;
import com.accenture.interview.controller.base.BaseController;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;

/**
 * The Class PaginationController.
 */
@RestController
public class PaginationController extends BaseController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;	
	
	/**
	 * Access page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/access")
	public ModelAndView accessPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("username", System.getProperty("user.name"));
		modelAndView.addObject("requestRegistrationTO", new RequestRegistrationTO());
		modelAndView.setViewName("access.html");
		return modelAndView;
	}
	
	/**
	 * Account page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/home")
	@Registered
	public ModelAndView accountPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.setViewName("home.html");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("inProgressInterviews", interviewFacade.getInProgressInterviewsNumber(username));
		modelAndView.addObject("myInterviews", interviewFacade.getMyInterviewsNumber(username));
		modelAndView.addObject("myInterviewsMonth", interviewFacade.getMonthCompletedInterviewsNumber(username));
		modelAndView.addObject("inProgressInterviewsMonth", interviewFacade.getMonthInProgressInterviewsNumber(username));
		modelAndView.addObject("yearMonthInterviews", interviewFacade.getYearCompletedInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		return modelAndView;
	}


	/**
	 * Show form.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/new")
	@Registered
	public ModelAndView insertInterview() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviewerList", interviewerFacade.findAllInterviewers());
		modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
		modelAndView.addObject("comboSitesDB", interviewFacade.getComboSites());
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.addObject("uploadCvTO", new UploadCvTO());
		modelAndView.setViewName("insert.html");
		return modelAndView;
	}

	/**
	 * Search interview.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/search")
	@Registered
	public ModelAndView searchInterview() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviewerList", interviewerFacade.findAllInterviewers());
		modelAndView.addObject("searchInterviewTO", new SearchInterviewTO());	
		modelAndView.addObject("comboSitesDB", interviewFacade.getComboSites());	
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("search.html");
		return modelAndView;
	}

	/**
	 * Show tech feedback form.
	 *
	 * @param idColloquio  the id colloquio
	 * @return the model and view
	 */
	@GetMapping("/feedback/technical")
	@Registered
	public ModelAndView showTechFeedbackForm(@RequestParam("idColloquio") String idColloquio) {
		ModelAndView modelAndView = new ModelAndView();
		updateInterviewId(Long.parseLong(idColloquio));
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("createTechFeedbackTO", new CreateTechFeedbackTO());
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("tech-feedback.html");
		return modelAndView;
	}

	/**
	 * Show insert motivation feedback form.
	 *
	 * @param idColloquio  the id colloquio
	 * @return the model and view
	 */
	@GetMapping("/feedback/motivational")
	@Registered
	public ModelAndView showInsertMotivationFeedbackForm(@RequestParam("idColloquio") String idColloquio) {
		ModelAndView modelAndView = new ModelAndView();
		updateInterviewId(Long.parseLong(idColloquio));
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("createMotivationFeedbackTO", new CreateMotivationFeedbackTO());
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("motivation-feedback.html");
		return modelAndView;
	}

	/**
	 * My interviews page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/completed")
	@Registered
	public ModelAndView myInterviewsPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviews", interviewFacade.getCompletedInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("completed.html");
		return modelAndView;
	}

	/**
	 * In progress page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/in-progress")
	@Registered
	public ModelAndView inProgressPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviews", interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.addObject("insertAvailabilityTO", new InsertAvailabilityTO());
		modelAndView.setViewName("in-progress-interviews.html");
		return modelAndView;
	}

	/**
	 * In progress page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/assigned")
	@Registered
	public ModelAndView assignedPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviewerList", interviewerFacade.findAllInterviewers());
		modelAndView.addObject("interviews", interviewFacade.getAssignedInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.addObject("approveAvailabilityTO", new ApproveAvailabilityTO());
		modelAndView.addObject("reassignInterviewTO", new ReassignInterviewTO());		
		modelAndView.setViewName("assigned.html");
		return modelAndView;
	}

}
