package com.accenture.interview.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.facade.FeedbackFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.RescheduledAvailabilityTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.constants.PaginationConstants;

/**
 * The Class PaginationController.
 */
@RestController
public class PaginationController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;	
	
   @Autowired
   private FeedbackFacade feedbackFacade;

	
	/**
    * My interviews page.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/interview/completed")
	public ModelAndView myInterviewsPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getCompletedInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("completed.html");
		return modelAndView;
	}

	/**
    * In progress page.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/interview/in-progress")
	public ModelAndView inProgressPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.INSERT_AVAILABILITY_TO, new InsertAvailabilityTO());
      modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      modelAndView.addObject("rescheduledAvailabilityTO", new RescheduledAvailabilityTO());
      modelAndView.setViewName("in-progress-interviews.html");
		return modelAndView;
	}

	
	
	/**
    * Users page.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/users")
	public ModelAndView usersPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));		
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllUsers());		
		modelAndView.addObject(PaginationConstants.INTERVIEWS, interviewFacade.getInProgressInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.MODIFY_USER_TO, new ModifyInterviewerTO());
      modelAndView.addObject(PaginationConstants.INSERT_AVAILABILITY_TO, new InsertAvailabilityTO());
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("users.html");
		return modelAndView;
	}
	
	
   /**
    * Redirect access.
    *
    * @return the model and view
    */
   private ModelAndView redirectAccess() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("accessUserTO", new AccessUserTO());
      modelAndView.addObject(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
      modelAndView.setViewName("access.html");
      return modelAndView;
   }
}
