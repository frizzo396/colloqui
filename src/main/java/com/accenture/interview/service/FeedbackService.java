package com.accenture.interview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.repository.MotivationFeedbackRepository;
import com.accenture.interview.repository.TechFeedbackRepository;
import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;

/**
 * The Class FeedbackService.
 */
@Service
public class FeedbackService {

	/** The motivation feedback repository. */
	@Autowired
	private MotivationFeedbackRepository motivationFeedbackRepository;

	/** The tech feedback repository. */
	@Autowired
	private TechFeedbackRepository techFeedbackRepository;

	/**
	 * Insert tech feedback.
	 *
	 * @param request     the request
	 * @param interview   the interview
	 * @param idColloquio the id colloquio
	 * @return the creates the tech feedback response
	 */
	public TechFeedback insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, Interview interview, int idColloquio) {
		TechFeedback techFeedback = new TechFeedback(createTechFeedbackTO);
		techFeedback.setInterview(interview);
		return techFeedbackRepository.save(techFeedback);
	}

	/**
	 * Insert motivation feedback.
	 *
	 * @param request     the request
	 * @param interview   the interview
	 * @param idColloquio the id colloquio
	 * @return the creates the motivation feedback response
	 */
	public MotivationFeedback insertMotivationFeedback(CreateMotivationFeedbackTO feedbackTO, Interview interview, int idColloquio) {
		MotivationFeedback motFeedback = new MotivationFeedback(feedbackTO);
		motFeedback.setInterview(interview);
		return motivationFeedbackRepository.save(motFeedback);
	}

	/**
	 * Gets the feedbacks.
	 *
	 * @param interviewList the interview list
	 * @return the feedbacks
	 */
	public List<InterviewAndFeedbackRTO> getFeedbacks(List<InterviewAndFeedbackRTO> interviewList) {
		List<InterviewAndFeedbackRTO> myInterviews = new ArrayList<>();
		for (InterviewAndFeedbackRTO interview : interviewList) {
			// 1 --> Motivazionale
			if (interview.getInterviewType() == 1) {
				MotivationalFeedbackRTO motivationalFeedback = motivationFeedbackRepository.getMotivationFeedbackRTOByIdInterview(interview.getIdColloquio());
				if (motivationalFeedback != null) {
					interview.setMotivationalFeedback(motivationalFeedback);
				}

			} else if (interview.getInterviewType() == 2) {
				TechnicalFeedbackRTO technicalFeedback = techFeedbackRepository.getTechFeedbackRTOByIdInterview(interview.getIdColloquio());
				if (technicalFeedback != null) {
					interview.setTechnicalFeedback(technicalFeedback);
				}
			}
			myInterviews.add(interview);
		}
		return myInterviews;
	}
}
