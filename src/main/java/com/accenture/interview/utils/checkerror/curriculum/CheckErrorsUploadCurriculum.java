package com.accenture.interview.utils.checkerror.curriculum;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.accenture.interview.exception.GenericException;
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
    * @param uploadCVTO the upload CVTO
    * @return the error RTO
    */
   public ErrorRTO validate(UploadCvTO uploadCVTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Set<ConstraintViolation<UploadCvTO>> violations = factory.getValidator().validate(uploadCVTO);

		try {
			if (!violations.isEmpty()) {	
            ConstraintViolation<UploadCvTO> violation = violations.stream().findFirst().orElseThrow(GenericException::new);
				String errorMsg = messageSource.getMessage(violation.getMessage(), null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
		} catch (GenericException e) {
			return new ErrorRTO("Errore generico");
		}
		return null;
	}

}
