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
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.interview.InsertAvailabilityTO;

/**
 * The Class CheckErrorsInsertAvailability.
 */
@Component
public class CheckErrorsInsertAvailability {
	
	/** The interview service. */
	@Autowired
	private InterviewService interviewService;
	

	/** The message source. */
	@Autowired
	private MessageSource messageSource;


	/**
	 * Validate.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(InsertAvailabilityTO insertAvailabilityTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<InsertAvailabilityTO>> violations = factory.getValidator().validate(insertAvailabilityTO);

		if (!violations.isEmpty()) {		
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		if(ObjectUtils.isEmpty(interviewService.findInterviewById(insertAvailabilityTO.getInterviewId()))) {
			String errorMsg = messageSource.getMessage("interview.error.not-found", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}

}
