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
 * The Class CheckErrorsRecoverPasswordInterviewer.
 */
@Component
public class CheckErrorsRecoverPasswordInterviewer {

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

      if (ObjectUtils.isEmpty(enterpriseId)) {
         return new ErrorRTO(messageSource.getMessage("interviewer.error.enterpriseid.not-empty", null, Locale.getDefault()));
      }
        
      InterviewerRTO interviewerRto = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		if (ObjectUtils.isEmpty(interviewerRto)) {
			return new ErrorRTO(messageSource.getMessage("recover.password.user.not-active", null, Locale.getDefault()));			
		}	
		return null;
	}

}
