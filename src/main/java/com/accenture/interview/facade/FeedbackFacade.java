package com.accenture.interview.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechnicalFeedback;
import com.accenture.interview.rto.feedback.CreateMotivationFeedbackRTO;
import com.accenture.interview.rto.feedback.CreateTechFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.enums.MailTypeEnum;

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

	/** The mail service. */
	@Autowired
	private MailService mailService;


	/**
	 * Insert tech feedback.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @param interviewId the id colloquio
	 * @return the creates the tech feedback response
	 */
	public CreateTechFeedbackRTO insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, Long interviewId) {
      InterviewRTO interview = interviewService.findInterviewWithMailParams(interviewId);
		TechnicalFeedback techFeedback = feedbackService.insertTechFeedback(createTechFeedbackTO, interviewId);
		
		interviewService.updateInterviewTechFeedback(interviewId, techFeedback, createTechFeedbackTO.getFinalFeedback());
		
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
				Arrays.asList(interview.getAssignerMail()), 
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), createTechFeedbackTO.getFinalFeedback(), createTechFeedbackTO.getComment()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()),
				WebPaths.ASSIGNED);
		mailService.sendMail(mailParams, MailTypeEnum.FEEDBACK_INSERT);
		return new CreateTechFeedbackRTO(createTechFeedbackTO);
	}

	/**
	 * Insert motivation feedback.
	 *
	 * @param feedbackTO the feedback TO
	 * @param interviewId the id colloquio
	 * @return the creates the motivation feedback response
	 */
	public CreateMotivationFeedbackRTO insertMotivationFeedback(CreateMotivationFeedbackTO feedbackTO, Long interviewId) {
		InterviewRTO interview = interviewService.findInterviewWithMailParams((long) interviewId);
		MotivationFeedback motFeedback = feedbackService.insertMotivationFeedback(feedbackTO, interviewId);
		interviewService.updateInterviewMotFeedback(interviewId, motFeedback, feedbackTO.getFinalFeedback());
		
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
				Arrays.asList(interview.getAssignerMail()), 
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), feedbackTO.getFinalFeedback(), feedbackTO.getComment()),
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
				WebPaths.ASSIGNED);
		mailService.sendMail(mailParams, MailTypeEnum.FEEDBACK_INSERT);	
		return new CreateMotivationFeedbackRTO(feedbackTO);
	}

}
