package com.accenture.interview.utils.checkerror;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;

/**
 * The Class CheckErrorsInsertMotivationFeedback.
 */
@Component
public class CheckErrorsInsertMotivationFeedback {

	/**
	 * Validate insert motivation feedback request.
	 *
	 * @param request the request
	 * @return the sets the
	 */
	public Set<String> validate(CreateMotivationFeedbackTO feedbackTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateMotivationFeedbackTO>> violations = factory.getValidator().validate(feedbackTO);

		if (!violations.isEmpty()) {
			return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
		}

		return new HashSet<>();
	}
}
