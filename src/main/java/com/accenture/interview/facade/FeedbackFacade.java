package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.rto.feedback.CreateMotivationFeedbackRTO;
import com.accenture.interview.rto.feedback.CreateTechFeedbackRTO;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;

/**
 * The Class FeedbackFacade.
 */
@Component
public class FeedbackFacade {

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/** The feedback service. */
	@Autowired
	private FeedbackService feedbackService;

	/**
	 * Insert tech feedback.
	 *
	 * @param request     the request
	 * @param idColloquio the id colloquio
	 * @return the creates the tech feedback response
	 */
	public CreateTechFeedbackRTO insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, int idColloquio) {
		Interview interview = interviewService.findInterviewById(idColloquio);
		TechFeedback techFeedback = feedbackService.insertTechFeedback(createTechFeedbackTO, interview, idColloquio);
		interviewService.updateInterviewTechFeedback(interview, techFeedback, createTechFeedbackTO.getFinalFeedback());
		return new CreateTechFeedbackRTO(createTechFeedbackTO);
	}

	/**
	 * Insert motivation feedback.
	 *
	 * @param request     the request
	 * @param idColloquio the id colloquio
	 * @return the creates the motivation feedback response
	 */
	public CreateMotivationFeedbackRTO insertMotivationFeedback(CreateMotivationFeedbackTO feedbackTO, int idColloquio) {
		Interview interview = interviewService.findInterviewById(idColloquio);
		MotivationFeedback motFeedback = feedbackService.insertMotivationFeedback(feedbackTO, interview, idColloquio);
		interviewService.updateInterviewMotFeedback(interview, motFeedback, feedbackTO.getFinalFeedback());
		return new CreateMotivationFeedbackRTO(feedbackTO);
	}

	/**
	 * Find interview by id.
	 *
	 * @param interviewId the interview id
	 * @return the interview
	 */
	public Interview findInterviewById(Integer interviewId) {
		return interviewService.findInterviewById(Long.parseLong(interviewId.toString()));
	}
}
