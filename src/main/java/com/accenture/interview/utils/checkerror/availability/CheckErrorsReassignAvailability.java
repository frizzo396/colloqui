package com.accenture.interview.utils.checkerror.availability;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.exception.GenericException;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.interview.ReassignInterviewTO;


/**
 * The Class CheckErrorsReassignAvailability.
 */
@Component
public class CheckErrorsReassignAvailability {

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;


	/**
	 * Validate.
	 *
	 * @param reassignTO the reassign TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(ReassignInterviewTO reassignTO) {		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<ReassignInterviewTO>> violations = factory.getValidator().validate(reassignTO);

		try {
			if (!violations.isEmpty()) {	
				ConstraintViolation<ReassignInterviewTO> violation = violations.stream().findFirst().orElseThrow(GenericException::new);
				String errorMsg = messageSource.getMessage(violation.getMessage(), null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
			if(ObjectUtils.isEmpty(interviewService.findInterviewById(reassignTO.getInterviewId()))) {
				String errorMsg = messageSource.getMessage("interview.error.not-found", null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
		} catch (GenericException e) {
			return new ErrorRTO("Errore generico");
		}
		return null;
	}

}
