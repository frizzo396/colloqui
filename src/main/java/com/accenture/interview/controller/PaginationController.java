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
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.constants.PaginationConstants;

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
		modelAndView.addObject(PaginationConstants.USERNAME, System.getProperty(PaginationConstants.USER_NAME));
		modelAndView.addObject(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.setViewName("home.html");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.IN_PROGRESS_INTERVIEWS, interviewFacade.getInProgressInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.MY_INTERVIEWS, interviewFacade.getMyInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.MY_INTERVIEWS_MONTH, interviewFacade.getMonthCompletedInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.IN_PROGRESS_INTERVIEWS_MONTH, interviewFacade.getMonthInProgressInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.YEAR_MONTH_INTERVIEWS, interviewFacade.getYearCompletedInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
		modelAndView.addObject(PaginationConstants.CREATE_INTERVIEW_TO, new CreateInterviewTO());
		modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
		modelAndView.addObject(PaginationConstants.SEARCH_INTERVIEW_TO, new SearchInterviewTO());	
		modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());	
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.CREATE_TECH_FEED_TO, new CreateTechFeedbackTO());
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.CREATE_MOT_FEED_TO, new CreateMotivationFeedbackTO());
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getCompletedInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
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
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.INSERT_AVAILABILITY_TO, new InsertAvailabilityTO());
		modelAndView.setViewName("in-progress-interviews.html");
		return modelAndView;
	}

	/**
	 * Assigned page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/interview/assigned")
	@Registered
	public ModelAndView assignedPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getAssignedInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.APPROVE_AVAILABILITY_TO, new ApproveAvailabilityTO());
		modelAndView.addObject(PaginationConstants.REASSIGN_INTERVIEW_TO, new ReassignInterviewTO());		
		modelAndView.setViewName("assigned.html");
		return modelAndView;
	}
	
	
	/**
	 * Users page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/users")
	@Registered
	public ModelAndView usersPage() {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty(PaginationConstants.USER_NAME);
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));		
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllUsers());		
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.MODIFY_USER_TO, new ModifyInterviewerTO());
		modelAndView.addObject(PaginationConstants.INSERT_AVAILABILITY_TO, new InsertAvailabilityTO());
		modelAndView.setViewName("users.html");
		return modelAndView;
	}
}
