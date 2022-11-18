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
import com.accenture.interview.to.interview.UploadCvTO;

/**
 * The Class CheckErrorsUploadCurriculum.
 */
@Component
public class CheckErrorsUploadCurriculum {
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Validate.
	 *
	 * @param uploadCvTO the upload cv TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(UploadCvTO uploadCvTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<UploadCvTO>> violations = factory.getValidator().validate(uploadCvTO);

		if (!violations.isEmpty()) {		
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}

}
