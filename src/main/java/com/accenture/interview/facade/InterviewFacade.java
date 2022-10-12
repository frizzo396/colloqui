package com.accenture.interview.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.entity.CandidateType;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.rto.interview.CreateInterviewResponse;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.SearchInterviewResponse;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;

/**
 * The Class InterviewFacade.
 */
@Component
public class InterviewFacade {

	/** The candidate service. */
	@Autowired
	private CandidateService candidateService;

	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;

	/** The feedback service. */
	@Autowired
	private FeedbackService feedbackService;

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/**
	 * The event service.
	 *
	 * @param request the request
	 * @return the creates the interview response
	 * @Autowired private EventService eventService;
	 */

	/**
	 * Adds the new interview.
	 *
	 * @param request the request
	 * @return the creates the interview response
	 */
	public CreateInterviewResponse addNewInterview(CreateInterviewTO request) {
		CreateInterviewResponse response = null;
		Optional<CandidateType> optCandidate = candidateService.getCandidateType(request.getCandidateType());
		Interviewer interviewer = interviewerService.findInterviewerByEnterpriseId(request.getEnterpriseId());
		
		/** if (!(ObjectUtils.isEmpty(interviewer)) && !(optCandidate.isEmpty())) { */
		if (!(ObjectUtils.isEmpty(interviewer)) && (optCandidate.isPresent())) {
			// Interview interview =
			interviewService.addNewInterview(request, optCandidate.get(), interviewer);
			CreateInterviewResponse createInterviewResponse = new CreateInterviewResponse(request);
			createInterviewResponse.setEnterpriseId(request.getEnterpriseId());
			// eventService.sendTeamsInvitation(interview.getScheduledDate(),
			// interview.getMail(), interview.getCandidateName(),
			// interview.getCandidateSurname());
			response = new CreateInterviewResponse(request);
		}

		return response;
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the list
	 */
	public List<SearchInterviewResponse> searchInterviews(SearchInterviewTO searchInterviewTO) {
		return interviewService.searchInterview(searchInterviewTO);
	}

	/**
	 * Gets the my interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews
	 */
	public List<InterviewAndFeedbackRTO> getMyInterviews(String enterpriseId) {
		List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getMyInterviews(enterpriseId);

		return feedbackService.getFeedbacks(interviewAndFeedbackList);
	}

	/**
	 * Gets the in progress interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews
	 */
	public List<Interview> getInProgressInterviews(String enterpriseId) {
		return interviewService.getInProgressInterviews(enterpriseId);
	}
}
