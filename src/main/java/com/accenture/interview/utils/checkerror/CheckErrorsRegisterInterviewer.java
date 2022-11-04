package com.accenture.interview.utils.checkerror;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;


/**
 * The Class CheckErrorsInsertInterview.
 */
@Component
public class CheckErrorsRegisterInterviewer {
										 

	/** The message source. */	
	@Autowired private MessageSource messageSource;
	 
	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;
	
	/**
	 * Validate insert interview request.
	 *
	 * @param createInterviewTO the create interview TO
	 * @return the sets the
	 */
	public ErrorRTO validate(RegisterInterviewerTO registerUserTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<RegisterInterviewerTO>> violations = factory.getValidator().validate(registerUserTO);

		if (!violations.isEmpty()) {
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}

		if(interviewerExists(registerUserTO.getEnterpriseId())) { 
			return new ErrorRTO(messageSource.getMessage("interviewer.notexists", null, Locale.getDefault())); 
		}	
		
		return null;
	}
	

	/**
	 * Interviewer exists.
	 *
	 * @param enterpriseId the enterprise id
	 * @return true, if successful
	 */
	private boolean interviewerExists(String enterpriseId) {
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		return interviewer != null;	
	}
		
}
