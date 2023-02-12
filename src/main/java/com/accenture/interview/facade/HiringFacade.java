package com.accenture.interview.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.service.FeedbackService;
import com.accenture.interview.service.InterviewService;

/**
 * The Class HiringFacade.
 */
@Component
public class HiringFacade {

   /** The interview service. */
   @Autowired
   private InterviewService interviewService;

   /** The feedback service. */
   @Autowired
   private FeedbackService feedbackService;

   /**
    * Gets the hiring candidates.
    *
    * @return the hiring candidates
    */
   public List<InterviewAndFeedbackRTO> getHiringCandidates() {
      List<InterviewAndFeedbackRTO> interviewAndFeedbackList = interviewService.getCompletedInterviews();
      return feedbackService.getFeedbacks(interviewAndFeedbackList);
   }

}
