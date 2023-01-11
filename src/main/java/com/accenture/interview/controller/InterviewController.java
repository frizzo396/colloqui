package com.accenture.interview.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.checkerror.interview.CheckErrorsInsertInterview;
import com.accenture.interview.utils.constants.PaginationConstants;

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


	/**
	 * Creates the interview.
	 *
	 * @param createInterviewTO the create interview TO
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
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @param model the model
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
		model.addAttribute("searchInterviews", interviewFacade.searchInterviews(searchInterviewTO));
		model.addAttribute("searchInterviewTO", new SearchInterviewTO());
		model.addAttribute("comboSitesDB", interviewFacade.getComboSites());
		model.addAttribute("interviewerList", interviewerFacade.findAllInterviewers());
		model.addAttribute("registerUserTO", new RegisterInterviewerTO());
		return "search";
	}

}
