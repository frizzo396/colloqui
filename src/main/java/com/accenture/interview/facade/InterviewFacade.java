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
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.rto.interview.UpdateInterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interview.AssignInterviewTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchAssignedTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.enums.InterviewTypeEnum;
import com.accenture.interview.utils.enums.MailTypeEnum;
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

   /** The availability service. */
   @Autowired
   private AvailabilityService availabilityService;

	/** The mail service. */
	@Autowired
	private MailService mailService;
	
   /** The mail utils. */
   @Autowired
   private MailUtils mailUtils;

   @Autowired
   private WebPaths webPaths;

	/**
	 * Gets the combo sites.
	 *
	 * @return the combo sites
	 */
	public List<SiteRTO> getComboSites() {
		return siteService.findAllSites();
	}

   /**
    * Find interview for update.
    *
    * @param id the id
    * @return the creates the interview TO
    */
   public CreateInterviewTO findInterviewForUpdate(Long id) {
      UpdateInterviewRTO interview = interviewService.findInterviewForUpdate(id);
      interview.setInterviewTypeString(interview.getInterviewType() == 1 ? InterviewTypeEnum.MOTIVAZIONALE.getDescription() : InterviewTypeEnum.TECNICO.getDescription());
      return new CreateInterviewTO(interview);
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
      List<String> responsibleMails = interviewerService.findAllManagement().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
      Long interviewId = interviewService.addNewInterview(request, candidateType, interviewer, site, assigner);
      response = new CreateInterviewRTO(request);
      response.setInterviewId(interviewId);
      if (!(ObjectUtils.isEmpty(interviewer))) {
         List<String> bodyParams = !ObjectUtils.isEmpty(request.getNote()) ? Arrays.asList(response.getCandidateName(), response.getCandidateSurname(), request.getNote())
               : Arrays.asList(response.getCandidateName(), response.getCandidateSurname());
         MailTypeEnum mailType = !ObjectUtils.isEmpty(request.getNote()) ? MailTypeEnum.INTERVIEW_INSERT : MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES;
			MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interviewer.getMail()), 
               responsibleMails.stream().collect(Collectors.toSet()),
               bodyParams,
               Arrays.asList(response.getCandidateName(), response.getCandidateSurname()), webPaths.getInProgressUrl());
         mailService.sendMail(mailParams, mailType);
		}
		return response;
	}

   /**
    * Update interview.
    *
    * @param request      the request
    * @param enterpriseId the enterprise id
    * @return the creates the interview RTO
    */
   public CreateInterviewRTO updateInterview(CreateInterviewTO request, String enterpriseId) {
      CreateInterviewRTO response = null;
      CandidateTypeRTO candidateType = candidateService.getCandidateType(request.getCandidateType());
      InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(request.getEnterpriseId());
      SiteRTO site = siteService.findSiteById(Long.parseLong(request.getSite()));
      String oldInterviewer = interviewService.updateInterview(request, candidateType, interviewer, site);
      response = new CreateInterviewRTO(request);
      response.setInterviewId(request.getInterviewId());

      if ((oldInterviewer == null && request.getEnterpriseId() != null) || (oldInterviewer != null && !oldInterviewer.equals(request.getEnterpriseId()))) {
         List<String> bodyParams = !ObjectUtils.isEmpty(request.getNote()) ? Arrays.asList(response.getCandidateName(), response.getCandidateSurname(), request.getNote())
               : Arrays.asList(response.getCandidateName(), response.getCandidateSurname());
         MailTypeEnum mailType = !ObjectUtils.isEmpty(request.getNote()) ? MailTypeEnum.INTERVIEW_INSERT : MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES;
         MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interviewer.getMail()),
               interviewerService.findAllManagement().stream().map(InterviewerRTO::getMail).collect(Collectors.toSet()),
               bodyParams,
               Arrays.asList(response.getCandidateName(), response.getCandidateSurname()), webPaths.getInProgressUrl());
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
    * Assign interview.
    *
    * @param assignInterviewTO the assign interview TO
    * @return the long
    */
   public Long assignInterview(AssignInterviewTO assignInterviewTO) {
      InterviewRTO interview = interviewService.assignInterview(assignInterviewTO);
      List<String> responsibleMails = interviewerService.findAllManagement().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
      if (!ObjectUtils.isEmpty(interview)) {
         List<String> bodyParams = mailUtils.checkBodyParamsToAssignInterview(interview, assignInterviewTO.getInterviewDate());
         responsibleMails.add(interview.getAssignerMail());
         MailTypeEnum mailType = mailUtils.checkMailTypeToAssignInterview(interview.getNote(), assignInterviewTO.getInterviewDate());
         MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()),
               responsibleMails.stream().collect(Collectors.toSet()),
               bodyParams,
               Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), webPaths.getInProgressUrl());
         mailService.sendMail(mailParams, mailType);
      }
      return null;
   }

   /**
    * Unassign interview.
    *
    * @param interviewId the interview id
    * @return the long
    */
   public Long unassignInterview(Long interviewId) {
      interviewService.unassignInterview(interviewId);
      return interviewId;
   }

   /**
    * Cancel interview.
    *
    * @param interviewId the interview id
    * @return the long
    */
   public Long cancelInterview(Long interviewId) {
      interviewService.cancelInterview(interviewId);
      return interviewId;
   }


}
