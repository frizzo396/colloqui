package com.accenture.interview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechnicalFeedback;
import com.accenture.interview.repository.feedback.MotivationFeedbackRepository;
import com.accenture.interview.repository.feedback.TechFeedbackRepository;
import com.accenture.interview.repository.interview.InterviewRepository;
import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.feedback.ScoreTechFeedbackTO;
import com.google.gson.Gson;

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
	
	/** The interview repository. */
	@Autowired
	private InterviewRepository interviewRepository;

	/**
	 * Insert tech feedback.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @param interview   the interview
	 * @param interviewId the id colloquio
	 * @return the creates the tech feedback response
	 */
	public TechnicalFeedback insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, Long interviewId) {
		
		// TRASFORMAZIONE IN JSON CAMPO SCORES - START
		List<ScoreTechFeedbackTO> listFeedTO = createTechFeedbackTO.getTechList();		
		List<ScoreTechFeedbackTO> filterList = new ArrayList<>();
		
		for(ScoreTechFeedbackTO feedScore : listFeedTO) {
			if (!feedScore.getTechnology().trim().equals("")) {
				filterList.add(feedScore);
			}
		}		
		String json = new Gson().toJson(filterList);
		createTechFeedbackTO.setScores(json);
		// TRASFORMAZIONE IN JSON CAMPO SCORES - END
		
		TechnicalFeedback techFeedback = new TechnicalFeedback(createTechFeedbackTO);
		Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);		
		if(optInterview.isPresent()) {
         Interview interview = optInterview.get();
         interview.setUpdatedDate(new Date());
         techFeedback.setInterview(interview);
         interviewRepository.save(interview);
		}
		return techFeedbackRepository.save(techFeedback);
	}

	/**
	 * Insert motivation feedback.
	 *
	 * @param feedbackTO the feedback TO
	 * @param interview   the interview
	 * @param interviewId the id colloquio
	 * @return the creates the motivation feedback response
	 */
	public MotivationFeedback insertMotivationFeedback(CreateMotivationFeedbackTO feedbackTO, Long interviewId) {
		MotivationFeedback motFeedback = new MotivationFeedback(feedbackTO);
		Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);		
		if(optInterview.isPresent()) {
         Interview interview = optInterview.get();
         interview.setUpdatedDate(new Date());
         motFeedback.setInterview(interview);
         interviewRepository.save(interview);
		}
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
