package com.accenture.interview.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.rto.init.InitSearchInterviewerRTO;
import com.accenture.interview.rto.init.InitSearchResponsibleRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.CandidateService;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.to.interview.SearchInterviewResponsibleTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.utils.enums.InterviewStatusEnum;
import com.accenture.interview.utils.enums.InterviewTypeEnum;

/**
 * The Class SearchInterviewFacade.
 */
@Component
public class SearchInterviewFacade {

   /** The interviewer service. */
   @Autowired
   private InterviewerService interviewerService;

   /** The feedback service. */
   @Autowired
   private FeedbackService feedbackService;

   /** The availability service. */
   @Autowired
   private AvailabilityService availabilityService;

   /** The interview service. */
   @Autowired
   private InterviewService interviewService;

   /** The candidate service. */
   @Autowired
   private CandidateService candidateService;

   /** The site service. */
   private SiteService siteService;

   /**
    * Search interviews interviewer.
    *
    * @param searchInterviewTO the search interview TO
    * @return the list
    */
   public List<InterviewAndFeedbackRTO> searchInterviewsInterviewer(SearchInterviewTO searchInterviewTO) {
      List<InterviewAndFeedbackRTO> interviews = interviewService.searchInterviews(searchInterviewTO);
      interviews = availabilityService.addAvailabilityDates(interviews);
      return feedbackService.getFeedbacks(interviews);
   }

   /**
    * Search interviews responsible.
    *
    * @param searchInterviewTO the search interview TO
    * @return the list
    */
   public List<InterviewAndFeedbackRTO> searchInterviewsResponsible(SearchInterviewResponsibleTO searchInterviewTO) {
      List<InterviewAndFeedbackRTO> interviews = interviewService.searchInterviews(searchInterviewTO);
      interviews = availabilityService.addAvailabilityDates(interviews);
      return feedbackService.getFeedbacks(interviews);
   }

   /**
    * Inits the search interviewer.
    *
    * @return the inits the search interviewer RTO
    */
   public InitSearchInterviewerRTO initSearchInterviewer() {
      InitSearchInterviewerRTO initRTO = new InitSearchInterviewerRTO();
      initRTO.setCandidateTypeList(candidateService.getCandidateTypeList());
      initRTO.setInterviewerList(interviewerService.findAllInterviewers());
      initRTO.setInterviewTypeList(InterviewTypeEnum.getInterviewTypeList());
      initRTO.setSiteList(siteService.findAllSites());
      return initRTO;
   }

   /**
    * Inits the search responsible.
    *
    * @return the inits the search responsible RTO
    */
   public InitSearchResponsibleRTO initSearchResponsible() {
      InitSearchResponsibleRTO initRTO = new InitSearchResponsibleRTO();
      initRTO.setStatusList(InterviewStatusEnum.getInterviewStatusList());
      initRTO.setCandidateTypeList(candidateService.getCandidateTypeList());
      initRTO.setInterviewerList(interviewerService.findAllInterviewers());
      initRTO.setInterviewTypeList(InterviewTypeEnum.getInterviewTypeList());
      initRTO.setSiteList(siteService.findAllSites());
      return initRTO;
   }
}
