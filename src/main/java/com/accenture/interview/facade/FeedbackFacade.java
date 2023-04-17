package com.accenture.interview.facade;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.TechnicalFeedback;
import com.accenture.interview.rto.feedback.CreateMotivationFeedbackRTO;
import com.accenture.interview.rto.feedback.CreateTechFeedbackRTO;
import com.accenture.interview.rto.feedback.MotivationalFeedbackRTO;
import com.accenture.interview.rto.feedback.TechnicalFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
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

   /** The interviewer service. */
   @Autowired
   private InterviewerService interviewerService;

	/** The feedback service. */
	@Autowired
	private FeedbackService feedbackService;

	/** The mail service. */
	@Autowired
	private MailService mailService;

   /** The web paths. */
   @Autowired
   private WebPaths webPaths;


	/**
    * Insert tech feedback.
    *
    * @param createTechFeedbackTO the create tech feedback TO
    * @param interviewId          the interview id
    * @return the creates the tech feedback RTO
    */
	public CreateTechFeedbackRTO insertTechFeedback(CreateTechFeedbackTO createTechFeedbackTO, Long interviewId) {
      InterviewRTO interview = interviewService.findInterviewWithMailParams(interviewId);
		TechnicalFeedback techFeedback = feedbackService.insertTechFeedback(createTechFeedbackTO, interviewId);
      List<String> responsibleMails = interviewerService.findAllManagement().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
		interviewService.updateInterviewTechFeedback(interviewId, techFeedback, createTechFeedbackTO.getFinalFeedback());
		
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
            responsibleMails.stream().collect(Collectors.toSet()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), createTechFeedbackTO.getFinalFeedback(), createTechFeedbackTO.getComment()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()),
            webPaths.getAssignedUrl());
		mailService.sendMail(mailParams, MailTypeEnum.FEEDBACK_INSERT);
		return new CreateTechFeedbackRTO(createTechFeedbackTO);
	}

	/**
    * Insert motivation feedback.
    *
    * @param feedbackTO  the feedback TO
    * @param interviewId the interview id
    * @return the creates the motivation feedback RTO
    */
	public CreateMotivationFeedbackRTO insertMotivationFeedback(CreateMotivationFeedbackTO feedbackTO, Long interviewId) {
		InterviewRTO interview = interviewService.findInterviewWithMailParams((long) interviewId);
		MotivationFeedback motFeedback = feedbackService.insertMotivationFeedback(feedbackTO, interviewId);
      List<String> responsibleMails = interviewerService.findAllManagement().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
		interviewService.updateInterviewMotFeedback(interviewId, motFeedback, feedbackTO.getFinalFeedback());
		
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()), 
            responsibleMails.stream().collect(Collectors.toSet()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), feedbackTO.getFinalFeedback(), feedbackTO.getComment()),
				Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), 
            webPaths.getAssignedUrl());
		mailService.sendMail(mailParams, MailTypeEnum.FEEDBACK_INSERT);	
		return new CreateMotivationFeedbackRTO(feedbackTO);
	}

   /**
    * Find motivational feedback.
    *
    * @param interviewId the interview id
    * @return the motivational feedback RTO
    */
   public MotivationalFeedbackRTO findMotivationalFeedback(Long interviewId) {
      return feedbackService.findMotivationalFeedback(interviewId);
   }

   /**
    * Find technical feedback.
    *
    * @param interviewId the interview id
    * @return the technical feedback RTO
    */
   public TechnicalFeedbackRTO findTechnicalFeedback(Long interviewId) {
      return feedbackService.findTechnicalFeedback(interviewId);
   }

}
