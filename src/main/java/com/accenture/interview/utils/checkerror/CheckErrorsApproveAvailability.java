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
import com.accenture.interview.to.interview.ApproveAvailabilityTO;

/**
 * The Class CheckErrorsInsertAvailability.
 */
@Component
public class CheckErrorsApproveAvailability {
	
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
	public ErrorRTO validate(ApproveAvailabilityTO approveAvailabilityTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<ApproveAvailabilityTO>> violations = factory.getValidator().validate(approveAvailabilityTO);

		if (!violations.isEmpty()) {		
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		if(ObjectUtils.isEmpty(interviewService.findInterviewById(approveAvailabilityTO.getInterviewId()))) {
			String errorMsg = messageSource.getMessage("availability.interview.notfound", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}

}
