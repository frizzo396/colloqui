package com.accenture.interview.controller2.interview;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.utils.checkerror.interview.CheckErrorsInsertInterview;

/**
 * The Class InterviewController.
 */
@RestController
@RequestMapping("/interview")
public class InterviewController2 {

   /** The interview facade. */
   @Autowired
   private InterviewFacade interviewFacade;

   /** The check errors insert interview. */
   @Autowired
   private CheckErrorsInsertInterview checkErrorsInsertInterview;

   /**
    * Inits the insert interview.
    *
    * @return the response entity
    */
   @GetMapping()
   public ResponseEntity<Object> initInsertInterview() {
      return new ResponseEntity<>(interviewFacade.initInsertInterview(), HttpStatus.OK);
   }

   /**
    * Insert interview.
    *
    * @param createInterviewTO the create interview TO
    * @param session           the session
    * @return the response entity
    */
   @PostMapping()
   public ResponseEntity<Object> insertInterview(@RequestBody CreateInterviewTO createInterviewTO, HttpSession session) {
      /*
       * if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
       * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
       */

      ErrorRTO errorRTO = checkErrorsInsertInterview.validate(createInterviewTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(errorRTO, HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewFacade.addNewInterview(createInterviewTO, (String) session.getAttribute("entId")), HttpStatus.OK);
   }


}
