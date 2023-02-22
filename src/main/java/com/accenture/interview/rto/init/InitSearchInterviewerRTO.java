package com.accenture.interview.rto.init;

import java.util.List;

import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.interview.InterviewTypeRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class InitSearchInterviewerRTO.
 */
@Getter
@Setter
public class InitSearchInterviewerRTO {

   /** The interview type list. */
   private List<InterviewTypeRTO> interviewTypeList;

   /** The candidate type list. */
   private List<CandidateTypeRTO> candidateTypeList;

   /** The interviewer list. */
   private List<InterviewerRTO> interviewerList;

   /** The site list. */
   private List<SiteRTO> siteList;

}
