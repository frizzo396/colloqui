package com.accenture.interview.controller.interview;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.AvailabilityFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
import com.accenture.interview.to.interview.RescheduledAvailabilityTO;
import com.accenture.interview.utils.checkerror.availability.CheckErrorsAcceptAvailability;
import com.accenture.interview.utils.checkerror.availability.CheckErrorsApproveAvailability;
import com.accenture.interview.utils.checkerror.availability.CheckErrorsInsertAvailability;
import com.accenture.interview.utils.checkerror.availability.CheckErrorsReassignAvailability;
import com.accenture.interview.utils.checkerror.availability.CheckErrorsRefuseAvailability;
import com.accenture.interview.utils.constants.PaginationConstants;

/**
 * The Class AvailabilityController.
 */
@RestController
@RequestMapping("/availability")
public class AvailabilityController {

   /** The availability facade. */
   @Autowired
   private AvailabilityFacade availabilityFacade;

   /** The check errors insert availability. */
   @Autowired
   private CheckErrorsInsertAvailability checkErrorsInsertAvailability;

   /** The check errors approve availability. */
   @Autowired
   private CheckErrorsApproveAvailability checkErrorsApproveAvailability;

   /** The check errors refuse availability. */
   @Autowired
   private CheckErrorsRefuseAvailability checkErrorsRefuseAvailability;

   /** The check errors reassign availability. */
   @Autowired
   private CheckErrorsReassignAvailability checkErrorsReassignAvailability;

   /** The check errors accept availability. */
   @Autowired
   private CheckErrorsAcceptAvailability checkErrorsAcceptAvailability;

   /**
    * Insert availability.
    *
    * @param insertAvailabilityTO the insert availability TO
    * @param session              the session
    * @return the response entity
    */
   @PostMapping("/insert")
   public ResponseEntity<Object> insertAvailability(@RequestBody @ModelAttribute InsertAvailabilityTO insertAvailabilityTO, HttpSession session) {
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsInsertAvailability.validate(insertAvailabilityTO);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.addAvailabiltyInterview(insertAvailabilityTO)), HttpStatus.OK);
   }

   /**
    * Approve availability.
    *
    * @param approveAvailabilityTO the approve availability TO
    * @param session               the session
    * @return the response entity
    */
   @PostMapping("/approve")
   public ResponseEntity<Object> approveAvailability(@RequestBody @ModelAttribute ApproveAvailabilityTO approveAvailabilityTO, HttpSession session) {				
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsApproveAvailability.validate(approveAvailabilityTO);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.approveAvailability(approveAvailabilityTO)), HttpStatus.OK);
   }

   /**
    * Refuse availability.
    *
    * @param interviewId the interview id
    * @param session     the session
    * @return the response entity
    */
   @PostMapping("/refuse")
   public ResponseEntity<Object> refuseAvailability(@RequestParam(value = "interviewId") Long interviewId, HttpSession session) {
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsRefuseAvailability.validate(interviewId);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.refuseAvailability(interviewId)), HttpStatus.OK);
   }

   /**
    * Reassign availability.
    *
    * @param reassignTO the reassign TO
    * @param session    the session
    * @return the response entity
    */
   @PostMapping("/reassign")
   public ResponseEntity<Object> reassignAvailability(@RequestBody @ModelAttribute ReassignInterviewTO reassignTO, HttpSession session) {
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsReassignAvailability.validate(reassignTO);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.reassignAvailability(reassignTO)), HttpStatus.OK);
   }

   /**
    * Accept rescheduled availability.
    *
    * @param rescheduleTO the reschedule TO
    * @param session      the session
    * @return the response entity
    */
   @PostMapping("/accept")
   public ResponseEntity<Object> acceptRescheduledAvailability(@RequestBody @ModelAttribute RescheduledAvailabilityTO rescheduleTO, HttpSession session) {
      if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ErrorRTO errorRTO = checkErrorsAcceptAvailability.validate(rescheduleTO);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.acceptRescheduled(rescheduleTO)), HttpStatus.OK);
   }

}
