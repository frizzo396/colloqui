package com.accenture.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.controller.base.BaseController;
import com.accenture.interview.entity.Site;
import com.accenture.interview.facade.FeedbackFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;

/**
 * The Class PaginationController.
 */
@RestController
public class PaginationController extends BaseController {

	/** The feedback facade. */
	@Autowired
	private FeedbackFacade feedbackFacade;

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	@Autowired
	private InterviewerFacade interviewerFacade;	
	
	/**
	 * Show form.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the model and view
	 */
	@GetMapping("/manage-interview")
	public ModelAndView manageInterviews() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
		modelAndView.addObject("searchInterviewTO", new SearchInterviewTO());
		modelAndView.setViewName("search-insert.html");
		
		/** 2022-10-14 NUOVA COLONNA site - START */
		List<Site> listSitesCombo = interviewFacade.getComboSites();
		modelAndView.addObject("comboSitesDB", listSitesCombo);
		/** 2022-10-14 NUOVA COLONNA site - END */
		
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
	public ModelAndView showInsertMotivationFeedbackForm(@RequestParam("idColloquio") String idColloquio) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		updateInterviewId(Integer.parseInt(idColloquio));
		String username = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
		modelAndView.addObject("createMotivationFeedbackTO", new CreateMotivationFeedbackTO());
		modelAndView.addObject("interview", feedbackFacade.findInterviewById(Integer.parseInt(idColloquio)));
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
		modelAndView.addObject("interviews", interviewFacade.getMyInterviews(username));
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
		modelAndView.addObject("interviews", interviewFacade.getInProgressInterviews(username));
		modelAndView.setViewName("in-progress-interviews.html");
		return modelAndView;
	}

}
