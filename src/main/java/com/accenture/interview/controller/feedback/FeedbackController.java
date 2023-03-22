package com.accenture.interview.controller.feedback;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.FeedbackFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.utils.checkerror.feedback.CheckErrorsInsertFeedback;

/**
 * The Class FeedbackController.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

   /** The feedback facade. */
   @Autowired
   private FeedbackFacade feedbackFacade;

   /** The check errors insert motivation feedback. */
   @Autowired
   private CheckErrorsInsertFeedback checkErrorsInsertFeedback;

   /**
    * Adds the motivation feedback.
    *
    * @param createMotivationFeedbackTO the create motivation
    *                                   feedback TO
    * @return the response entity
    */
   @PostMapping("/motivational/insert")
   public ResponseEntity<Object> addMotivationFeedback(@RequestBody CreateMotivationFeedbackTO createMotivationFeedbackTO, HttpSession session) {
      ErrorRTO errorRTO = checkErrorsInsertFeedback.validate(createMotivationFeedbackTO, (String) session.getAttribute("entId"));
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(new BaseResponseRTO(feedbackFacade.insertMotivationFeedback(createMotivationFeedbackTO)), HttpStatus.OK);

   }

   /**
    * Adds the tech feedback.
    *
    * @param createTechFeedbackTO the create tech feedback TO
    * @return the response entity
    */
   @PostMapping("/technical/insert")
   public ResponseEntity<Object> addTechFeedback(@RequestBody CreateTechFeedbackTO createTechFeedbackTO, HttpSession session) {
      ErrorRTO errorRTO = checkErrorsInsertFeedback.validate(createTechFeedbackTO, (String) session.getAttribute("entId"));
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(new BaseResponseRTO(feedbackFacade.insertTechFeedback(createTechFeedbackTO)), HttpStatus.OK);
   }
}
