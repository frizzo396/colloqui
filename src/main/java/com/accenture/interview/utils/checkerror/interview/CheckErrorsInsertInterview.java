package com.accenture.interview.utils.checkerror.interview;

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
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.interview.CreateInterviewTO;

/**
 * The Class CheckErrorsInsertInterview.
 */
@Component
public class CheckErrorsInsertInterview {

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/** The candidate service. */
	@Autowired
	private CandidateService candidateService;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
    * Validate.
    *
    * @param createInterviewTO the create interview TO
    * @return the error RTO
    */
	public ErrorRTO validate(CreateInterviewTO createInterviewTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateInterviewTO>> violations = factory.getValidator().validate(createInterviewTO);

		try {
			if (!violations.isEmpty()) {	
				ConstraintViolation<CreateInterviewTO> violation = violations.stream().findFirst().orElseThrow(GenericException::new);
				String errorMsg = messageSource.getMessage(violation.getMessage(), null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}

			if(interviewAlreadyExists(createInterviewTO.getMail())) {
				return new ErrorRTO(messageSource.getMessage("interview.error.already-exists", null, Locale.getDefault()));
			}

			if(!candidateTypeExists(createInterviewTO.getCandidateType())) {
				return new ErrorRTO(messageSource.getMessage("interview.error.candidate.type.invalid", null, Locale.getDefault()));
			}

		} catch (GenericException e) {
			return new ErrorRTO("Errore generico");
		}
		return null;
	}


	/**
	 * Interview already exists.
	 *
	 * @param mail the mail
	 * @return true, if successful
	 */
	private boolean interviewAlreadyExists(String mail) {
		return !ObjectUtils.isEmpty(interviewService.findInterviewByMail(mail));
	}


	/**
    * Candidate type exists.
    *
    * @param candidateTypeId the candidate type id
    * @return true, if successful
    */
   private boolean candidateTypeExists(Long candidateTypeId) {
      return candidateService.getCandidateType(candidateTypeId) != null;
	}

}
