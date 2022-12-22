package com.accenture.interview.utils.checkerror.interviewer;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewerService;

/**
 * The Class CheckErrorAccessInterviewer.
 */
@Component
public class CheckErrorsAccessInterviewer {
	
	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;
										 
	/** The message source. */	
	@Autowired 
	private MessageSource messageSource;

	/**
	 * Validate.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the error RTO
	 */
	public ErrorRTO validate(String enterpriseId) {			
		InterviewerRTO interviewerRto = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		if (ObjectUtils.isEmpty(interviewerRto)) {
			return new ErrorRTO(messageSource.getMessage("access.user.not-registered", null, Locale.getDefault()));			
		}		
		return null;
	}

}
