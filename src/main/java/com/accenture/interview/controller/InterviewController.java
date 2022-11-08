package com.accenture.interview.controller;

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

import com.accenture.interview.facade.AvailabilityFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.utils.checkerror.CheckErrorsApproveAvailability;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertAvailability;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertInterview;

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
	
	/** The availability facade. */
	@Autowired
	private AvailabilityFacade availabilityFacade;

	/** The check errors insert interview. */
	@Autowired
	private CheckErrorsInsertInterview checkErrorsInsertInterview;
	
	/** The check errors insert availability. */
	@Autowired
	private CheckErrorsInsertAvailability checkErrorsInsertAvailability;
	
	@Autowired
	private CheckErrorsApproveAvailability checkErrorsApproveAvailability;


	/**
	 * Creates the interview.
	 *
	 * @param createInterviewTO the create interview TO
	 * @return the response entity
	 */
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Object> createInterview(@RequestBody @ModelAttribute CreateInterviewTO createInterviewTO) {
		ErrorRTO errorRTO = checkErrorsInsertInterview.validate(createInterviewTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.addNewInterview(createInterviewTO)), HttpStatus.OK);
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview request
	 * @param model the model
	 * @return the response entity
	 */
	@PostMapping("/search")
	public String searchInterview(@RequestBody @ModelAttribute SearchInterviewTO searchInterviewTO, Model model) {
		model.addAttribute("interviewer", interviewerFacade.interviewerInfo(System.getProperty("user.name")));
		model.addAttribute("searchInterviews", interviewFacade.searchInterviews(searchInterviewTO));
		model.addAttribute("searchInterviewTO", new SearchInterviewTO());
		model.addAttribute("comboSitesDB", interviewFacade.getComboSites());	
		model.addAttribute("registerUserTO", new RegisterInterviewerTO());
		return "search";
	}
	
	
	/**
	 * Insert availability.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the response entity
	 */
	@PostMapping("/insert-availability")
	@ResponseBody
	public ResponseEntity<Object> insertAvailability(@RequestBody @ModelAttribute InsertAvailabilityTO insertAvailabilityTO) {
		ErrorRTO errorRTO = checkErrorsInsertAvailability.validate(insertAvailabilityTO);
		
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.addAvailabiltyInterview(insertAvailabilityTO)), HttpStatus.OK);
	}
	
	/**
	 * Insert availability.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the response entity
	 */
	@PostMapping("/approve-availability")
	@ResponseBody
	public ResponseEntity<Object> approveAvailability(@RequestBody @ModelAttribute ApproveAvailabilityTO approveAvailabilityTO) {
		ErrorRTO errorRTO = checkErrorsApproveAvailability.validate(approveAvailabilityTO);
		
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.approveAvailability(approveAvailabilityTO)), HttpStatus.OK);
	}

}
