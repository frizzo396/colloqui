package com.accenture.interview.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;

/**
 * The Class InterviewerController.
 */
@Controller
@RequestMapping("/hiring")
public class HiringController {

   /** The interviewer facade. */
   @Autowired
   private InterviewFacade interviewFacade;
    
   
   /**
    * List of candidates.
    *
    * @return the completed interviews with final feedback OK or STAND-BY.
    */
   /*
   @GetMapping("/candidates")
   public List<InterviewAndFeedbackRTO> candidatesInfo() {
	   List<InterviewAndFeedbackRTO> result = interviewFacade.getHiringCandidates();	   
	   return result;
   }
   */
   
   @RequestMapping(value = "/candidates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody List<InterviewAndFeedbackRTO> candidatesInfo() {
	   List<InterviewAndFeedbackRTO> result = interviewFacade.getHiringCandidates();	   
	   return result;
   }   
   
}
