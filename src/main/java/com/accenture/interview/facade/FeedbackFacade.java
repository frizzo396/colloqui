package com.accenture.interview.facade;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.rto.feedback.CreateMotivationFeedbackRTO;
import com.accenture.interview.rto.feedback.CreateTechFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.service.EmailService;
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
	
	@Autowired
	private EmailService mailService;

	/**
	 * Insert tech feedback.
	 *
	 * @param request     the request
	 * @param idColloquio the id colloquio
	 * @return the creates the tech feedback response
	 */
	public CreateTechFeedbackRTO insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, int idColloquio) {
		InterviewRTO interview = interviewService.findInterviewForApproval((long) idColloquio);
		TechFeedback techFeedback = feedbackService.insertTechFeedback(createTechFeedbackTO, idColloquio);
		interviewService.updateInterviewTechFeedback(idColloquio, techFeedback, createTechFeedbackTO.getFinalFeedback());
		try {
			mailService.sendMail(interview.getAssignerMail(), interview.getInterviewerMail(), interview.getAssignerMail(), 
					MailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
					MailUtils.createInsertFeedbackBody(interview));
		} catch (MessagingException e) {
			return new CreateTechFeedbackRTO(createTechFeedbackTO);
		}
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
		InterviewRTO interview = interviewService.findInterviewForApproval((long) idColloquio);
		MotivationFeedback motFeedback = feedbackService.insertMotivationFeedback(feedbackTO, idColloquio);
		interviewService.updateInterviewMotFeedback(idColloquio, motFeedback, feedbackTO.getFinalFeedback());
		try {
			mailService.sendMail(interview.getAssignerMail(), interview.getInterviewerMail(), interview.getAssignerMail(), 
					MailUtils.createInterviewSubject(interview.getCandidateName(), interview.getCandidateSurname()), 
					MailUtils.createInsertFeedbackBody(interview));
		} catch (MessagingException e) {
			return new CreateMotivationFeedbackRTO(feedbackTO);
		}
		return new CreateMotivationFeedbackRTO(feedbackTO);
	}

}
