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
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckErrorsInsertMotivationFeedback.
 */
@Component
public class CheckErrorsInsertFeedback {
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Validate.
	 *
	 * @param feedbackTO the feedback TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(CreateMotivationFeedbackTO feedbackTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateMotivationFeedbackTO>> violations = factory.getValidator().validate(feedbackTO);

		if (!violations.isEmpty()) {
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}

		return null;
	}
	
	/**
	 * Validate.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(CreateTechFeedbackTO createTechFeedbackTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateTechFeedbackTO>> violations = factory.getValidator().validate(createTechFeedbackTO);

		if (!violations.isEmpty()) {
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}
}
