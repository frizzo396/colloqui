package com.accenture.interview.utils.checkerror;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.feedback.ScoreTechFeedbackTO;

/**
 * The Class CheckErrorsInsertMotivationFeedback.
 */
@Component
public class CheckErrorsInsertFeedback {
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/**
	 * Validate.
	 *
	 * @param feedbackTO the feedback TO
	 * @param interviewId the interview id
	 * @return the error RTO
	 */
	public ErrorRTO validate(CreateMotivationFeedbackTO feedbackTO, Long interviewId) {
		String errorMsg = null;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateMotivationFeedbackTO>> violations = factory.getValidator().validate(feedbackTO);

		if (!violations.isEmpty()) {
			errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
			
		if(!isInterviewer(interviewId)) {
			errorMsg = messageSource.getMessage("feedback.error.interviewer.not-equal", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}
	
	/**
	 * Validate.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @param interviewId the interview id
	 * @return the error RTO
	 */
	public ErrorRTO validate(CreateTechFeedbackTO createTechFeedbackTO, Long interviewId) {
		String errorMsg = null;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateTechFeedbackTO>> violations = factory.getValidator().validate(createTechFeedbackTO);
		
		if (!violations.isEmpty()) {
			errorMsg = messageSource.getMessage(violations.stream().findFirst().get().getMessage(), null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		
		List<ScoreTechFeedbackTO> filtered = createTechFeedbackTO.getTechList().stream()
				.filter(a -> (!ObjectUtils.isEmpty(a.getTechnology())))
				.collect(Collectors.toList());
		if(CollectionUtils.isEmpty(filtered)) {
			errorMsg = messageSource.getMessage("feedback.error.score.almost-one", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		
		createTechFeedbackTO.setTechList(filtered);		
				
		ErrorRTO techListErr = validateTechnologiesList(createTechFeedbackTO.getTechList());
		if(!ObjectUtils.isEmpty(techListErr)) {
			return techListErr;
		}
				
		if(!isInterviewer(interviewId)) {
			errorMsg = messageSource.getMessage("feedback.interviewer.notequal", null, Locale.getDefault());
			return new ErrorRTO(errorMsg);
		}
		return null;
	}
	
	
	private ErrorRTO validateTechnologiesList(List<ScoreTechFeedbackTO> techList) {
		String errorMsg = null;
		for (ScoreTechFeedbackTO scoreTO : techList) {
			if(!ObjectUtils.isEmpty(scoreTO.getTechnology()) && ObjectUtils.isEmpty(scoreTO.getScore())) {
				errorMsg = messageSource.getMessage("feedback.error.score.not-empty", null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
			
			if(!isNumeric(scoreTO.getScore())) {
				errorMsg = messageSource.getMessage("feedback.error.score.not-numeric", null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
			
			if(Integer.parseInt(scoreTO.getScore()) > 10) {
				errorMsg = messageSource.getMessage("feedback.error.max-score.value", null, Locale.getDefault());
				return new ErrorRTO(errorMsg);
			}
			
		}
		return null;
	}
	
	
	/**
	 * Checks if is interviewer.
	 *
	 * @param interviewId the interview id
	 * @return true, if is interviewer
	 */
	private boolean isInterviewer(Long interviewId) {
		Interview interview = interviewService.findInterviewById(interviewId);
		
		if(!ObjectUtils.isEmpty(interview)) {
			String enterpriseId = interview.getInterviewerId().getEnterpriseId();
			if(enterpriseId.equals(System.getProperty("user.name"))) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean isNumeric(String score) {
		try {
			Integer.parseInt(score);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}
