package com.accenture.interview.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.rto.feedback.CreateMotivationFeedbackRTO;
import com.accenture.interview.rto.feedback.CreateTechFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.MailService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.utils.mail.MailUtils;

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

	/** The mail utils. */
	@Autowired
	private MailUtils mailUtils;

	/**
	 * Insert tech feedback.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @param interviewId the id colloquio
	 * @return the creates the tech feedback response
	 */
	public CreateTechFeedbackRTO insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, Long interviewId) {
		InterviewRTO interview = interviewService.findInterviewForApproval((long) interviewId);
		TechFeedback techFeedback = feedbackService.insertTechFeedback(createTechFeedbackTO, interviewId);
		interviewService.updateInterviewTechFeedback(interviewId, techFeedback, createTechFeedbackTO.getFinalFeedback());

		mailService.sendMail(interview.getAssignerMail(), interview.getInterviewerMail(), interview.getAssignerMail(), 
				mailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
				mailUtils.createInsertFeedbackBody(interview.getCandidateName(), interview.getCandidateSurname()));
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
		InterviewRTO interview = interviewService.findInterviewForApproval((long) interviewId);
		MotivationFeedback motFeedback = feedbackService.insertMotivationFeedback(feedbackTO, interviewId);
		interviewService.updateInterviewMotFeedback(interviewId, motFeedback, feedbackTO.getFinalFeedback());

		mailService.sendMail(interview.getAssignerMail(), interview.getInterviewerMail(), interview.getAssignerMail(), 
				mailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
				mailUtils.createInsertFeedbackBody(interview.getCandidateName(), interview.getCandidateSurname()));

		return new CreateMotivationFeedbackRTO(feedbackTO);
	}

}
