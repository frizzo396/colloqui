package com.accenture.interview.utils.checkerror.interviewer;

import java.util.Locale;
import java.util.Optional;
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
import com.accenture.interview.to.interviewer.RecoverPasswordTO;

/**
 * The Class CheckErrorAccessInterviewer.
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
	public ErrorRTO validate(RecoverPasswordTO recoverPasswordTO) {			

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<RecoverPasswordTO>> violations = factory.getValidator().validate(recoverPasswordTO);

		if (!violations.isEmpty()) {	
			Optional<ConstraintViolation<RecoverPasswordTO>> optViolation = violations.stream().findFirst();

			if(optViolation.isPresent()) {
				String errorMsg = messageSource.getMessage(optViolation.get().getMessage(), null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
		}
        
		InterviewerRTO interviewerRto = 
		interviewerService.findInterviewerByEnterpriseIdAndMail(recoverPasswordTO.getEnterpriseId(), recoverPasswordTO.getMail());
		if (ObjectUtils.isEmpty(interviewerRto)) {
			return new ErrorRTO(messageSource.getMessage("recover.password.user.not-active", null, Locale.getDefault()));			
		}	

		return null;
	}

}
