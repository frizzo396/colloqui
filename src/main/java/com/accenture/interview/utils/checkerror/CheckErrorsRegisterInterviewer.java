package com.accenture.interview.utils.checkerror;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;


/**
 * The Class CheckErrorsInsertInterview.
 */
@Component
public class CheckErrorsRegisterInterviewer {
	
	@Autowired
	private InterviewerService interviewerService;
										 

	/** The message source. */	
	@Autowired 
	private MessageSource messageSource;
	 
	
	/**
	 * Validate insert interview request.
	 *
	 * @param registerUserTO the register user TO
	 * @return the sets the
	 */
	public ErrorRTO validate(RegisterInterviewerTO registerUserTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<RegisterInterviewerTO>> violations = factory.getValidator().validate(registerUserTO);

		if (!violations.isEmpty()) {
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		
		return null;
	}
	
	/**
	 * Validate.
	 *
	 * @param registerUserTO the register user TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(RequestRegistrationTO registerUserTO) {
		String errorMsg = null;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<RequestRegistrationTO>> violations = factory.getValidator().validate(registerUserTO);

		if (!violations.isEmpty()) {
			errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		
		InterviewerRTO interviewer = interviewerService.findInterviewerByMail(registerUserTO.getMail());
		
		if(!ObjectUtils.isEmpty(interviewer)) {
			errorMsg = messageSource.getMessage("interviewer.error.mail.already-exists", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		
		return null;
	}
	
		
}
