package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.controller.base.BaseController;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;

/**
 * The Class PaginationController.
 */
@RestController
public class PaginationController extends BaseController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	@Autowired
	private InterviewerFacade interviewerFacade;	
	
	
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
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		return modelAndView;
	}

	
	/**
	 * Show form.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/interview/new")
	public ModelAndView insertInterview() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
		modelAndView.addObject("comboSitesDB", interviewFacade.getComboSites());
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("insert.html");
		return modelAndView;
	}
	
	@GetMapping("/interview/search")
	public ModelAndView searchInterview() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
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
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/feedback/technical")
	public ModelAndView showTechFeedbackForm(@RequestParam("idColloquio") String idColloquio) {
		ModelAndView modelAndView = new ModelAndView();
		updateInterviewId(Integer.parseInt(idColloquio));
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
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@GetMapping("/feedback/motivational")
	public ModelAndView showInsertMotivationFeedbackForm(@RequestParam("idColloquio") String idColloquio) {
		ModelAndView modelAndView = new ModelAndView();
		updateInterviewId(Integer.parseInt(idColloquio));
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
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/interview/completed")
	public ModelAndView myInterviewsPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviews", interviewFacade.getCompletedInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("my-interviews.html");
		return modelAndView;
	}

	/**
	 * In progress page.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/interview/in-progress")
	public ModelAndView inProgressPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("interviews", interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.setViewName("in-progress-interviews.html");
		return modelAndView;
	}

}
