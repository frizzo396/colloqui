package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.interview.annotation.Registered;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.checkerror.CheckErrorsRegisterInterviewer;

/**
 * The Class InterviewerController.
 */
@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;
	
	/** The check errors insert interview. */
	@Autowired 
	private CheckErrorsRegisterInterviewer checkErrorsRegisterInterviewer;
	 


	/**
	 * Interviewer info.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interviewer
	 */
	@GetMapping("/getUserInfo")
	@Registered
	public InterviewerRTO interviewerInfo(@RequestParam("enterpriseId") String enterpriseId) {
		return interviewerFacade.interviewerInfo(enterpriseId);
	}
	
	
	/**
	 * Creates the interviewer.
	 *
	 * @param registerUserTO the register user TO
	 * @return the response entity
	 */
	@PostMapping("/register")
	@Registered
	public @ResponseBody ResponseEntity<Object> registerInterviewer(@RequestBody @ModelAttribute RegisterInterviewerTO registerUserTO) {		
		ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(registerUserTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(interviewerFacade.addNewInterviewer(registerUserTO), HttpStatus.OK);		
	}
	
	/**
	 * Modifies the interviewer type.
	 *
	 * @param modifyUserTO the user TO
	 * @return the response entity
	 */
	@PostMapping("/modify")
	@Registered
	public @ResponseBody ResponseEntity<Object> modifyInterviewer(@RequestBody @ModelAttribute ModifyInterviewerTO modifyUserTO) {		
		ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(modifyUserTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(interviewerFacade.modifyInterviewer(modifyUserTO), HttpStatus.OK);		
	}
	
	/**
	 * Modifies the interviewer status.
	 *
	 * @param modifyUserTO the user TO
	 * @return the response entity
	 */
	@PostMapping("/enable-disable")
	@Registered
	public @ResponseBody ResponseEntity<Object> enableDisableInterviewer(@RequestParam("userIdParam") long id) {	
		ModifyInterviewerTO modifyUserTO = new ModifyInterviewerTO();
		modifyUserTO.setId(id);
		
		return new ResponseEntity<>(interviewerFacade.enableDisableInterviewer(modifyUserTO), HttpStatus.OK);
	}	
	
	/**
	 * Request registration.
	 *
	 * @param requestTO the request TO
	 * @return the response entity
	 */
	@PostMapping("/register/request")
	public @ResponseBody ResponseEntity<Object> requestRegistration(@RequestBody @ModelAttribute RequestRegistrationTO requestTO) {
		ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(requestTO);
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(interviewerFacade.requestRegistration(requestTO), HttpStatus.OK);		
	}	

}
