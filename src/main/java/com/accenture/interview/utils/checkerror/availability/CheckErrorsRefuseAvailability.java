package com.accenture.interview.utils.checkerror.availability;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.service.InterviewService;

/**
 * The Class CheckErrorsRefuseAvailability.
 */
@Component
public class CheckErrorsRefuseAvailability {
	
	/** The interview service. */
	@Autowired
	private InterviewService interviewService;
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Validate.
	 *
	 * @param interviewId the interview id
	 * @return the error RTO
	 */
	public ErrorRTO validate(Long interviewId) {		
		if(ObjectUtils.isEmpty(interviewId)) { 
			return new ErrorRTO(messageSource.getMessage("availability.error.refuse.interview.empty", null, Locale.getDefault()));
		}
		if(ObjectUtils.isEmpty(interviewService.findInterviewById(interviewId))){
			return new ErrorRTO(messageSource.getMessage("interview.error.not-found", null, Locale.getDefault()));
		}
		return null;
	}

}
