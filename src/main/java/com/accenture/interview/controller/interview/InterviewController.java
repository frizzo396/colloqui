package com.accenture.interview.controller.interview;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.facade.SearchInterviewFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.AssignInterviewTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewResponsibleTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.checkerror.interview.CheckErrorsAssignInterview;
import com.accenture.interview.utils.checkerror.interview.CheckErrorsInsertInterview;
import com.accenture.interview.utils.constants.PaginationConstants;
import com.accenture.interview.utils.enums.InterviewStatusEnum;

/**
 * The Class InterviewController.
 */
@Controller
@RequestMapping("/interview")
public class InterviewController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;

	/** The check errors insert interview. */
	@Autowired
	private CheckErrorsInsertInterview checkErrorsInsertInterview;

   /** The check errors assign interview. */
   @Autowired
   private CheckErrorsAssignInterview checkErrorsAssignInterview;

   /** The interview facade. */
   @Autowired
   private SearchInterviewFacade searchInterviewFacade;


	/**
    * Creates the interview.
    *
    * @param createInterviewTO the create interview TO
    * @param session           the session
    * @return the response entity
    */
	@PostMapping(path = "/create")
	public @ResponseBody ResponseEntity<Object> createInterview(@RequestBody @ModelAttribute CreateInterviewTO createInterviewTO, HttpSession session) {
	   if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
	      return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
	   }
	   ErrorRTO errorRTO = checkErrorsInsertInterview.validate(createInterviewTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.addNewInterview(createInterviewTO, (String) session.getAttribute("entId"))), HttpStatus.OK);
	}

   /**
    * Assign interview.
    *
    * @param assignInterviewTO the assign interview TO
    * @param session           the session
    * @return the response entity
    */
   @PostMapping(path = "/assign")
   public @ResponseBody ResponseEntity<Object> assignInterview(@RequestBody @ModelAttribute AssignInterviewTO assignInterviewTO, HttpSession session) {
      if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsAssignInterview.validate(assignInterviewTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.assignInterview(assignInterviewTO)), HttpStatus.OK);
   }

   /**
    * Unassign interview.
    *
    * @param interviewId the interview id
    * @param session     the session
    * @return the response entity
    */
   @PostMapping(path = "{interviewId}/unassign")
   public String unassignInterview(@PathVariable(name = "interviewId") Long interviewId, Model model, HttpSession session) {

      if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         model.addAttribute("accessUserTO", new AccessUserTO());
         model.addAttribute(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
         return "access";
      }
      interviewFacade.unassignInterview(interviewId);
      String username = (String) session.getAttribute("entId");
      model.addAttribute(PaginationConstants.INTERVIEWER, interviewerFacade.interviewerInfo(username));
      model.addAttribute(PaginationConstants.INTERVIEWER_LIST, interviewerFacade.findAllInterviewers());
      model.addAttribute(PaginationConstants.COMBO_SITES, interviewFacade.getComboSites());
      // model.addAttribute("searchInterviews",
      // searchInterviewFacade.searchInterviewsResponsible(new
      // SearchInterviewResponsibleTO("", "", "", "", "", null,
      // "")));
      model.addAttribute("searchAssignedTO", new SearchInterviewResponsibleTO());
      model.addAttribute("comboStatus", InterviewStatusEnum.getInterviewStatusList());
      model.addAttribute("assignInterviewTO", new AssignInterviewTO());
      model.addAttribute(PaginationConstants.REGISTER_USER_TO, new RegisterInterviewerTO());
      model.addAttribute(PaginationConstants.APPROVE_AVAILABILITY_TO, new ApproveAvailabilityTO());
      model.addAttribute(PaginationConstants.REASSIGN_INTERVIEW_TO, new ReassignInterviewTO());
      model.addAttribute(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      model.addAttribute(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
      return "assigned";
   }


	/**
    * Search interview.
    *
    * @param searchInterviewTO the search interview TO
    * @param model             the model
    * @param session           the session
    * @return the string
    */
	@PostMapping("/search")
	public String searchInterview(@RequestBody @ModelAttribute SearchInterviewTO searchInterviewTO, Model model, HttpSession session) {
		if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
		   model.addAttribute("accessUserTO", new AccessUserTO());
		   model.addAttribute(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
		   return "access";
		}	   
      model.addAttribute("interviewer", interviewerFacade.interviewerInfo((String) session.getAttribute("entId")));
      model.addAttribute("searchInterviews", searchInterviewFacade.searchInterviewsInterviewer(searchInterviewTO));
      model.addAttribute("searchInterviewTO", new SearchInterviewTO());
      model.addAttribute("comboSitesDB", interviewFacade.getComboSites());
      model.addAttribute("interviewerList", interviewerFacade.findAllInterviewers());
      model.addAttribute("registerUserTO", new RegisterInterviewerTO());
      model.addAttribute(PaginationConstants.APPROVE_AVAILABILITY_TO, new ApproveAvailabilityTO());
      model.addAttribute(PaginationConstants.REASSIGN_INTERVIEW_TO, new ReassignInterviewTO());
      model.addAttribute(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      return "search";
	}

   /**
    * Search assigned interview.
    *
    * @param searchInterviewTO the search interview TO
    * @param model             the model
    * @param session           the session
    * @return the string
    */
   @PostMapping("/search-assigned")
   public String searchAssignedInterview(@RequestBody @ModelAttribute SearchInterviewResponsibleTO searchInterviewTO, Model model, HttpSession session) {
      if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         model.addAttribute("accessUserTO", new AccessUserTO());
         model.addAttribute(PaginationConstants.REQUEST_REGISTRATION_TO, new RequestRegistrationTO());
         return "access";
      }
      model.addAttribute("interviewer", interviewerFacade.interviewerInfo((String) session.getAttribute("entId")));
      model.addAttribute("searchInterviews", searchInterviewFacade.searchInterviewsResponsible(searchInterviewTO));
      model.addAttribute("searchAssignedTO", new SearchInterviewResponsibleTO());
      model.addAttribute("comboSitesDB", interviewFacade.getComboSites());
      model.addAttribute("comboStatus", InterviewStatusEnum.getInterviewStatusList());
      model.addAttribute("interviewerList", interviewerFacade.findAllInterviewers());
      model.addAttribute("registerUserTO", new RegisterInterviewerTO());
      model.addAttribute("assignInterviewTO", new AssignInterviewTO());
      model.addAttribute(PaginationConstants.UPLOAD_CV_TO, new UploadCvTO());
      model.addAttribute(PaginationConstants.APPROVE_AVAILABILITY_TO, new ApproveAvailabilityTO());
      model.addAttribute(PaginationConstants.REASSIGN_INTERVIEW_TO, new ReassignInterviewTO());
      model.addAttribute(PaginationConstants.CHANGE_PASSWORD_INTERVIEWER_TO, new ChangePasswordInterviewerTO());
      return "assigned";
   }

}
