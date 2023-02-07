package com.accenture.interview.facade;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.interview.CreateInterviewRTO;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewMonthRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchAssignedTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.enums.MailTypeEnum;

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

   /** The availability service. */
   @Autowired
   private AvailabilityService availabilityService;

	/** The mail service. */
	@Autowired
	private MailService mailService;
	
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
    * @param request      the request
    * @param enterpriseId the enterprise id
    * @return the creates the interview RTO
    */
	public CreateInterviewRTO addNewInterview(CreateInterviewTO request, String enterpriseId) {
		CreateInterviewRTO response = null;
		CandidateTypeRTO candidateType = candidateService.getCandidateType(request.getCandidateType());
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(request.getEnterpriseId());
		InterviewerRTO assigner = interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		SiteRTO site = siteService.findSiteById(Long.parseLong(request.getSite()));
      List<String> responsibleMails = interviewerService.getAllResponsibles().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
		if (!(ObjectUtils.isEmpty(interviewer))) {
			Long interviewId = interviewService.addNewInterview(request, candidateType, interviewer, site, assigner);
			response = new CreateInterviewRTO(request);
         response.setInterviewId(interviewId);
         List<String> bodyParams = !ObjectUtils.isEmpty(request.getNote()) ? Arrays.asList(response.getCandidateName(), response.getCandidateSurname(), request.getNote())
               : Arrays.asList(response.getCandidateName(), response.getCandidateSurname());
         MailTypeEnum mailType = !ObjectUtils.isEmpty(request.getNote()) ? MailTypeEnum.INTERVIEW_INSERT : MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES;
			MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interviewer.getMail()), 
               responsibleMails,
               bodyParams,
					Arrays.asList(response.getCandidateName(), response.getCandidateSurname()), WebPaths.IN_PROGRESS);
         mailService.sendMail(mailParams, mailType);
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
      interviews = availabilityService.addAvailabilityDates(interviews);
		return feedbackService.getFeedbacks(interviews);
	}

   /**
    * Search assigned interviews.
    *
    * @param searchInterviewTO the search interview TO
    * @return the list
    */
   public List<InterviewAndFeedbackRTO> searchAssignedInterviews(SearchAssignedTO searchInterviewTO) {
      String site = searchInterviewTO.getSite().replace(",", "");
      searchInterviewTO.setSite(site);
      List<InterviewAndFeedbackRTO> interviews = interviewService.searchAssignedInterviews(searchInterviewTO);
      interviews = availabilityService.addAvailabilityDates(interviews);
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
	 * @return the assigned interviews
	 */
   public List<InterviewAndFeedbackRTO> getAssignedInterviews() {
      List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getAssignedInterviews();
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
	
	/**
	 * Gets the in progress interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews number
	 */
	public Integer getInProgressInterviewsNumber(String enterpriseId) {
		return interviewService.getInProgressInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the my interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews number
	 */
	public Integer getMyInterviewsNumber(String enterpriseId) {
		return interviewService.getMyInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the month in progress interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the month in progress interviews number
	 */
	public Integer getMonthInProgressInterviewsNumber(String enterpriseId) {
		return interviewService.getInProgressInterviewsMonthCount(enterpriseId);
	}

	/**
	 * Gets the month completed interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the month completed interviews number
	 */
	public Integer getMonthCompletedInterviewsNumber(String enterpriseId) {
		return interviewService.getMyInterviewsMonthCount(enterpriseId);
	}

	/**
	 * Gets the year completed interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the year completed interviews
	 */
	public List<InterviewMonthRTO> getYearCompletedInterviews(String enterpriseId) {
		return interviewService.getCompletedYearInterviews(enterpriseId);
	}
	
	/**
	 * Gets completed interviews with final feedback OK or STAND-BY.
	 *
	 * @return the completed interviews with status COMPLETED and final feedback OK or STAND-BY
	 */	
	public List<InterviewAndFeedbackRTO> getHiringCandidates() {
		List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getCompletedInterviews();		
		return feedbackService.getFeedbacks(interviewAndFeedbackList);
	}
}
