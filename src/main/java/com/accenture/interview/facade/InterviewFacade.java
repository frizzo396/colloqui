package com.accenture.interview.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.interview.CreateInterviewRTO;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.SearchInterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;

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

	/**
	 * The event service.
	 *
	 * @param request the request
	 * @return the creates the interview response
	 * @Autowired private EventService eventService;
	 */
	
	/**
	 * Return list of sites.
	 *
	 * @return list of sites from table site
	 */
	public List<SiteRTO> getComboSites() {
		return siteService.findAllSites();
	}
	
	/**
	 * Adds the new interview.
	 *
	 * @param request the request
	 * @return the creates the interview response
	 */
	public CreateInterviewRTO addNewInterview(CreateInterviewTO request) {
		CreateInterviewRTO response = null;
		CandidateTypeRTO candidateType = candidateService.getCandidateType(request.getCandidateType());
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(request.getEnterpriseId());
		
		if (!(ObjectUtils.isEmpty(interviewer)) && !(ObjectUtils.isEmpty(candidateType))) {
			// Interview interview =
			interviewService.addNewInterview(request, candidateType, interviewer);
			CreateInterviewRTO createInterviewResponse = new CreateInterviewRTO(request);
			createInterviewResponse.setEnterpriseId(request.getEnterpriseId());
			// eventService.sendTeamsInvitation(interview.getScheduledDate(),
			// interview.getMail(), interview.getCandidateName(),
			// interview.getCandidateSurname());
			response = new CreateInterviewRTO(request);
		}
		return response;
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the list
	 */
	public List<SearchInterviewRTO> searchInterviews(SearchInterviewTO searchInterviewTO) {
		return interviewService.searchInterview(searchInterviewTO);
	}

	/**
	 * Gets the my interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews
	 */
	public List<InterviewAndFeedbackRTO> getCompletedInterviews(String enterpriseId) {
		List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getMyInterviews(enterpriseId);
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
