package com.accenture.interview.utils.checkerror;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.accenture.interview.to.feedback.CreateTechFeedbackTO;

/**
 * The Class CheckErrorsInsertTechFeedback.
 */
@Component
public class CheckErrorsInsertTechFeedback {

	/**
	 * Validate insert tech feedback request.
	 *
	 * @param request the request
	 * @return the sets the
	 */
	public Set<String> validate(CreateTechFeedbackTO createTechFeedbackTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateTechFeedbackTO>> violations = factory.getValidator().validate(createTechFeedbackTO);

		if (!violations.isEmpty()) {
			return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
		}

		return new HashSet<>();
	}

}
