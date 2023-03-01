package com.accenture.interview.controller.base;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.facade.FeedbackFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.feedback.ScoreTechFeedbackTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.AssignInterviewTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
import com.accenture.interview.to.interview.RescheduledAvailabilityTO;
import com.accenture.interview.to.interview.SearchAssignedTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RecoverPasswordTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.constants.PaginationConstants;
import com.accenture.interview.utils.enums.CandidateTypeEnum;
import com.accenture.interview.utils.enums.InterviewStatusEnum;
import com.accenture.interview.utils.enums.InterviewTypeEnum;
import com.accenture.interview.utils.enums.InterviewerTypeEnum;
import com.accenture.interview.utils.enums.SiteEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
   @Autowired
   private FeedbackFacade feedbackFacade;

	/**
	 * Access page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/access")
	public ModelAndView accessPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("accessUserTO", new AccessUserTO());
		modelAndView.addObject(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
		modelAndView.setViewName("access.html");
		return modelAndView;
	}
	
	/**
	 * Recover password page.
	 *
	 * @return the model and view
	 */
	@GetMapping("/recover-password")
	public ModelAndView recoverPasswordPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("recoverPasswordTO", new RecoverPasswordTO());
		modelAndView.setViewName("recover-password.html");
		return modelAndView;
	}	
	
   /**
    * Logout.
    *
    * @param session the session
    * @return the model and view
    */
   @PostMapping("/logout")
   public ModelAndView logout(HttpSession session) {
      ModelAndView modelAndView = new ModelAndView();
      session.removeAttribute("entId");
      session.invalidate();
      modelAndView.addObject("accessUserTO", new AccessUserTO());
      modelAndView.addObject(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
      modelAndView.setViewName("access.html");
      return modelAndView;
   }

	/**
    * Account page.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/home")
	public ModelAndView accountPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.setViewName("home.html");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.IN_PROGRESS_INTERVIEWS, interviewFacade.getInProgressInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.MY_INTERVIEWS, interviewFacade.getMyInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.MY_INTERVIEWS_MONTH, interviewFacade.getMonthCompletedInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.IN_PROGRESS_INTERVIEWS_MONTH, interviewFacade.getMonthInProgressInterviewsNumber(username));
		modelAndView.addObject(PaginationConstants.YEAR_MONTH_INTERVIEWS, interviewFacade.getYearCompletedInterviews(username));
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		return modelAndView;
	}


	/**
    * Insert interview.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/interview/new")
	public ModelAndView insertInterview(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
		modelAndView.addObject(PaginationConstants.CREATE_INTERVIEW_TO, new CreateInterviewTO());
		modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		modelAndView.addObject(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("insert.html");
		return modelAndView;
	}

   @GetMapping("/interview/{interviewId}/edit")
   public ModelAndView editInterview(@PathVariable(name = "interviewId") Long interviewId, HttpSession session) {
      ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return redirectAccess();
      }
      String username = (String) session.getAttribute("entId");
      CreateInterviewTO interview = interviewFacade.findInterviewForUpdate(interviewId);
      modelAndView.addObject("interviewTypeList", InterviewTypeEnum.getTypeList());
      modelAndView.addObject("candidateTypeList", CandidateTypeEnum.getTypeList());
      modelAndView.addObject("siteId", SiteEnum.getIdFromDescription(interview.getSite()));
      modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
      modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
      modelAndView.addObject(PaginationConstants.CREATE_INTERVIEW_TO, interview);
      modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());
      modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
      modelAndView.addObject(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
      modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      modelAndView.setViewName("edit.html");
      return modelAndView;
   }

	/**
    * Search interview.
    *
    * @param session the session
    * @return the model and view
    */
	@GetMapping("/interview/search")
	public ModelAndView searchInterview(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
		modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
      modelAndView.addObject(PaginationConstants.SEARCH_INTERVIEW_TO, new SearchInterviewTO());
		modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());	
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("search.html");
		return modelAndView;
	}

	/**
    * Show tech feedback form.
    *
    * @param idColloquio the id colloquio
    * @param session     the session
    * @return the model and view
    * @throws JsonProcessingException
    * @throws JsonMappingException
    */
	@GetMapping("/feedback/technical")
   public ModelAndView showTechFeedbackForm(@RequestParam("idColloquio") String idColloquio, HttpSession session) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		updateInterviewId(Long.parseLong(idColloquio));
      modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
      TechnicalFeedbackRTO techFeedback = feedbackFacade.findTechnicalFeedback(Long.parseLong(idColloquio));
      if(!ObjectUtils.isEmpty(techFeedback)) {
         CreateTechFeedbackTO createTechFeedbackTO = new CreateTechFeedbackTO();
         List<ScoreTechFeedbackTO> scores = new ObjectMapper().readValue(techFeedback.getScores(), new TypeReference<List<ScoreTechFeedbackTO>>() {
         });
         createTechFeedbackTO.setTechList(scores);
         createTechFeedbackTO.setScores(techFeedback.getScores());
         createTechFeedbackTO.setComment(techFeedback.getComment());
         modelAndView.addObject(PaginationConstants.CREATE_TECH_FEED_TO, createTechFeedbackTO);
      } else {
         modelAndView.addObject(PaginationConstants.CREATE_TECH_FEED_TO, new CreateTechFeedbackTO());
      }
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("tech-feedback.html");
		return modelAndView;
	}

	/**
    * Show insert motivation feedback form.
    *
    * @param idColloquio the id colloquio
    * @param session     the session
    * @return the model and view
    */
	@GetMapping("/feedback/motivational")
	public ModelAndView showInsertMotivationFeedbackForm(@RequestParam("idColloquio") String idColloquio, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		MotivationalFeedbackRTO motFeedbackRTO = feedbackFacade.findMotivationalFeedback(Long.parseLong(idColloquio));
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
		updateInterviewId(Long.parseLong(idColloquio));
		modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));

      modelAndView.addObject(PaginationConstants.CREATE_MOT_FEED_TO,
            !ObjectUtils.isEmpty(motFeedbackRTO) ? new CreateMotivationFeedbackTO(motFeedbackRTO.getStanding(), motFeedbackRTO.getSchoolBackground(), motFeedbackRTO.getMotivation(),
                  motFeedbackRTO.getSoftSkills(), motFeedbackRTO.getEnglishLevel(), motFeedbackRTO.getLogicQuestion(), motFeedbackRTO.getTechQuestion(), "", motFeedbackRTO.getComment())
                  : new CreateMotivationFeedbackTO());
		modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
		
		modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
		modelAndView.setViewName("motivation-feedback.html");
		return modelAndView;
	}

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
    * Assigned page.
    *
    * @param session the session
    * @return the model and view
    */
   @GetMapping("/interview/search-assigned")
	public ModelAndView assignedPage(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
      if (session.isNew() || ObjectUtils.isEmpty(session.getAttribute("entId"))) {
			return redirectAccess();
		}
		String username = (String) session.getAttribute("entId");
      InterviewerRTO interviewer = interviewerFacade.interviewerInfo(username);
      modelAndView.addObject(PaginationConstants.INTERVIEWER, interviewer);
      modelAndView.addObject(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
      modelAndView.addObject(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());
      modelAndView.addObject(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
      modelAndView.addObject(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      if (interviewer.getType() != InterviewerTypeEnum.RESPONSABILE.getId()) {
         modelAndView.addObject(PaginationConstants.SEARCH_INTERVIEW_TO, new SearchInterviewTO());
         modelAndView.setViewName("search.html");
      } else {
         modelAndView.addObject("searchInterviews", interviewFacade.searchAssignedInterviews(new SearchAssignedTO("", "", "", "", "", null, "")));
         modelAndView.addObject("searchAssignedTO", new SearchAssignedTO());
         modelAndView.addObject("comboStatus", InterviewStatusEnum.getInterviewStatusList());
         modelAndView.addObject("assignInterviewTO", new AssignInterviewTO());
         modelAndView.addObject(PaginationConstants.APPROVE_AVAILABILITY_TO, new ApproveAvailabilityTO());
         modelAndView.addObject(PaginationConstants.REASSIGN_INTERVIEW_TO, new ReassignInterviewTO());
         modelAndView.addObject(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
         modelAndView.setViewName("assigned.html");
      }
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
