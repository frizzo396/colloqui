package com.accenture.interview.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.interview.CreateInterviewRTO;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.MailService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.utils.mail.MailUtils;

/**
 * The Class InterviewFacade.
 */
@Component
public class InterviewFacade {

	/** The site service. */
	@Autowired
	private SiteService siteService;	

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

	/** The mail service. */
	@Autowired
	private MailService mailService;
	
	/** The mail utils. */
	@Autowired
	private MailUtils mailUtils;



	/**
	 * Gets the combo sites.
	 *
	 * @return the combo sites
	 */
	public List<SiteRTO> getComboSites() {
		return siteService.findAllSites();
	}

	/**
	 * Adds the new interview.
	 *
	 * @param request the request
	 * @return the creates the interview RTO
	 */
	public CreateInterviewRTO addNewInterview(CreateInterviewTO request) {
		CreateInterviewRTO response = null;
		CandidateTypeRTO candidateType = candidateService.getCandidateType(request.getCandidateType());
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(request.getEnterpriseId());
		InterviewerRTO assigner = interviewerService.findInterviewerByEnterpriseId(System.getProperty("user.name"));
		SiteRTO site = siteService.findSiteById(Long.parseLong(request.getSite()));

		if (!(ObjectUtils.isEmpty(interviewer)) 
				&& !(ObjectUtils.isEmpty(candidateType)) 
				&& !(ObjectUtils.isEmpty(site))
				&& !(ObjectUtils.isEmpty(assigner))) {
			interviewService.addNewInterview(request, candidateType, interviewer, site, assigner);
			CreateInterviewRTO createInterviewResponse = new CreateInterviewRTO(request);
			createInterviewResponse.setEnterpriseId(request.getEnterpriseId());
			response = new CreateInterviewRTO(request);

			mailService.sendMail(assigner.getMail(), interviewer.getMail(), assigner.getMail(), 
					mailUtils.createInterviewSubject(response.getCandidateName(), response.getCandidateSurname()), 
					mailUtils.createInsertInterviewBody(response.getCandidateName(), response.getCandidateSurname()));
		}
		return response;
	}

	/**
	 * Search interviews.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the list
	 */
	public List<InterviewAndFeedbackRTO> searchInterviews(SearchInterviewTO searchInterviewTO) {
		List<InterviewAndFeedbackRTO> interviews = interviewService.searchInterview(searchInterviewTO);
		return feedbackService.getFeedbacks(interviews);
	}

	/**
	 * Gets the completed interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the completed interviews
	 */
	public List<InterviewAndFeedbackRTO> getCompletedInterviews(String enterpriseId) {
		List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getCompletedInterviews(enterpriseId);
		return feedbackService.getFeedbacks(interviewAndFeedbackList);
	}

	/**
	 * Gets the assigned interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the assigned interviews
	 */
	public List<InterviewAndFeedbackRTO> getAssignedInterviews(String enterpriseId) {
		InterviewerRTO assigner = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getAssignedInterviews(assigner.getId());
		return feedbackService.getFeedbacks(interviewAndFeedbackList);
	}

	/**
	 * Gets the in progress interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews
	 */
	public List<InProgressInterviewRTO> getInProgressInterviews(String enterpriseId) {
		return interviewService.getInProgressInterviews(enterpriseId);
	}
}
