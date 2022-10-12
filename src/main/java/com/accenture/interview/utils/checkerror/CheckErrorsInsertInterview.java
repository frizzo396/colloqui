package com.accenture.interview.utils.checkerror;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.entity.CandidateType;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.Interviewer;
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

	/**
	 * Validate insert interview request.
	 *
	 * @param request the request
	 * @return the sets the
	 */
	public Set<String> validate(CreateInterviewTO createInterviewTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Set<ConstraintViolation<CreateInterviewTO>> violations = factory.getValidator().validate(createInterviewTO);

		if (!violations.isEmpty()) {
			return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
		}

		Interview interview = interviewService.findInterviewByNameSurnameAndMail(createInterviewTO.getCandidateName(), createInterviewTO.getCandidateSurname(), createInterviewTO.getMail());
		if (!ObjectUtils.isEmpty(interview)) {
			return new HashSet<>(Arrays.asList("Intervista già presente."));
		}
		Optional<CandidateType> candidateType = candidateService.getCandidateType(createInterviewTO.getCandidateType());
		
		/** if (candidateType.isEmpty()) { */
		if (!(candidateType.isPresent())) {
			return new HashSet<>(Arrays.asList("Tipo di candidato non valido."));
		}
		Interviewer interviewer = interviewerService.findInterviewerByEnterpriseId(createInterviewTO.getEnterpriseId());
		if (ObjectUtils.isEmpty(interviewer)) {
			return new HashSet<>(Arrays.asList("Intervistatore non presente."));
		}
		return new HashSet<>();
	}

}
