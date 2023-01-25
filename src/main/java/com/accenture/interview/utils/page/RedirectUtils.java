package com.accenture.interview.utils.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.constants.PaginationConstants;

/**
 * The Class RedirectUtils.
 */
@Component
public class RedirectUtils {

   /** The interviewer facade. */
   @Autowired
   private InterviewerFacade interviewerFacade;

   /** The interview facade. */
   @Autowired
   private InterviewFacade interviewFacade;

   /**
    * Redirect home.
    *
    * @param username the username
    * @return the model and view
    */
   public ModelAndView redirectHome(String username) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
      modelAndView.addObject("inProgressInterviews", interviewFacade.getInProgressInterviewsNumber(username));
      modelAndView.addObject("myInterviews", interviewFacade.getMyInterviewsNumber(username));
      modelAndView.addObject("myInterviewsMonth", interviewFacade.getMonthCompletedInterviewsNumber(username));
      modelAndView.addObject("inProgressInterviewsMonth", interviewFacade.getMonthInProgressInterviewsNumber(username));
      modelAndView.addObject("yearMonthInterviews", interviewFacade.getYearCompletedInterviews(username));
      modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
      
      modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      
      modelAndView.setViewName("home.html");
      return modelAndView;
   }

   /**
    * Redirect insert.
    *
    * @param username the username
    * @param errorMessage the error message
    * @param interviewId the interview id
    * @return the model and view
    */
   public ModelAndView redirectInsert(String username, String errorMessage, Long interviewId) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
      modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
      modelAndView.addObject("interviewerList", interviewerFacade.findAllInterviewers());
      modelAndView.addObject("comboSitesDB", interviewFacade.getComboSites());
      modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
      modelAndView.addObject("uploadCvTO", new UploadCvTO());
      modelAndView.addObject("uploadError", errorMessage);
      modelAndView.addObject("createdInterview", interviewId);
      
      modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      
      modelAndView.setViewName("insert.html");
      return modelAndView;
   }
   
   
   /**
    * Redirect access.
    *
    * @return the model and view
    */
   public ModelAndView redirectAccess() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("accessUserTO", new AccessUserTO());
      modelAndView.addObject(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
      modelAndView.setViewName("access.html");
      return modelAndView;
   }

}
