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

import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
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

	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Validate insert interview request.
	 *
	 * @param createInterviewTO the create interview TO
	 * @return the sets the
	 */
	public ErrorRTO validate(CreateInterviewTO createInterviewTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateInterviewTO>> violations = factory.getValidator().validate(createInterviewTO);

		if (!violations.isEmpty()) {		
			String errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}

		if(interviewAlreadyExists(createInterviewTO.getMail())) {
			return new ErrorRTO(messageSource.getMessage("interview.error.already-exists", null, Locale.getDefault()));
		}
		
		if(!candidateTypeExists(createInterviewTO.getCandidateType())) {
			return new ErrorRTO(messageSource.getMessage("interview.error.candidate.type.invalid", null, Locale.getDefault()));
		}

		if(!interviewerExists(createInterviewTO.getEnterpriseId())) {
			return new ErrorRTO(messageSource.getMessage("interviewer.not-found", null, Locale.getDefault())); 
		}
		return null;
	}
	
	
	/**
	 * Interview already exists.
	 *
	 * @param name the name
	 * @param surname the surname
	 * @param mail the mail
	 * @return true, if successful
	 */
	private boolean interviewAlreadyExists(String mail) {
		return !ObjectUtils.isEmpty(interviewService.findInterviewByMail(mail));
	}
	
	
	/**
	 * Interviewer exists.
	 *
	 * @param enterpriseId the enterprise id
	 * @return true, if successful
	 */
	private boolean interviewerExists(String enterpriseId) {
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		return interviewer != null;	
	}
	
	
	/**
	 * Candidate type exists.
	 *
	 * @param candidateTypeName the candidate type name
	 * @return true, if successful
	 */
	private boolean candidateTypeExists(String candidateTypeName) {
		CandidateTypeRTO candidateType = candidateService.getCandidateType(candidateTypeName);
		return candidateType != null;	
	}

}
