package com.accenture.interview.controller.interviewer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RecoverPasswordTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsAccessInterviewer;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsRecoverPasswordInterviewer;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsRegisterInterviewer;
import com.accenture.interview.utils.constants.PaginationConstants;

/**
 * The Class InterviewerController.
 */
@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

   /** The interviewer facade. */
   @Autowired
   private InterviewerFacade interviewerFacade;

   /** The check errors register interviewer. */
   @Autowired 
   private CheckErrorsRegisterInterviewer checkErrorsRegisterInterviewer;

   /** The check errors access interviewer. */
   @Autowired
   private CheckErrorsAccessInterviewer checkErrorsAccessInterviewer;
   
   /** The check errors recover password interviewer. */
   @Autowired
   private CheckErrorsRecoverPasswordInterviewer checkErrorsRecoverPasswordInterviewer;   
   

   /**
    * Interviewer info.
    *
    * @param enterpriseId the enterprise id
    * @return the interviewer RTO
    */
   @GetMapping("/getUserInfo")
   public InterviewerRTO interviewerInfo(@RequestParam("enterpriseId") String enterpriseId) {
      return interviewerFacade.interviewerInfo(enterpriseId);
   }

   /**
    * Register interviewer.
    *
    * @param registerUserTO the register user TO
    * @param session the session
    * @return the response entity
    */
   @PostMapping("/register")
   public @ResponseBody ResponseEntity<Object> registerInterviewer(@RequestBody @ModelAttribute RegisterInterviewerTO registerUserTO, HttpSession session) {		
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(registerUserTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(interviewerFacade.addNewInterviewer(registerUserTO), HttpStatus.OK);		
   }

   /**
    * Modify interviewer.
    *
    * @param modifyUserTO the modify user TO
    * @return the response entity
    */
   @PostMapping("/modify")
   public @ResponseBody ResponseEntity<Object> modifyInterviewer(@RequestBody @ModelAttribute ModifyInterviewerTO modifyUserTO, HttpSession session) {		
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(modifyUserTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(interviewerFacade.modifyInterviewer(modifyUserTO), HttpStatus.OK);		
   }  
   
   /**
    * Change password of the interviewer.
    *
    * @param modifyUserTO the modify user TO
    * @return the response entity
    */
   @PostMapping("/change-pwd")
   public @ResponseBody ResponseEntity<Object> changePasswordInterviewer(@RequestBody @ModelAttribute ChangePasswordInterviewerTO changePasswordInterviewerTO, HttpSession session) {		
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }   
      
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(changePasswordInterviewerTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      
      // per effettuare il cambio password
      return new ResponseEntity<>(interviewerFacade.changePasswordInterviewer(changePasswordInterviewerTO), HttpStatus.OK);
   }   

   /**
    * Enable disable interviewer.
    *
    * @param id the id
    * @return the response entity
    */
   @PostMapping("/enable-disable")
   public @ResponseBody ResponseEntity<Object> enableDisableInterviewer(@RequestParam("userIdParam") long id, HttpSession session) {	
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      ModifyInterviewerTO modifyUserTO = new ModifyInterviewerTO();
      modifyUserTO.setId(id);
      return new ResponseEntity<>(interviewerFacade.enableDisableInterviewer(modifyUserTO), HttpStatus.OK);
   }	

   /**
    * Request registration.
    *
    * @param requestTO the request TO
    * @return the response entity
    */
   @PostMapping("/register/request")
   public @ResponseBody ResponseEntity<Object> requestRegistration(@RequestBody @ModelAttribute RequestRegistrationTO requestTO) {
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(requestTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      return new ResponseEntity<>(interviewerFacade.requestRegistration(requestTO), HttpStatus.OK);		
   }	


   /**
    * Access interviewer.
    *
    * @param accessUserTO the access user TO
    * @param session the session
    * @return the response entity
    */
   @PostMapping("/access")
   public @ResponseBody ResponseEntity<Object> accessInterviewer(@RequestBody @ModelAttribute AccessUserTO accessUserTO, HttpSession session) {		
      ErrorRTO errorRTO = checkErrorsAccessInterviewer.validate(accessUserTO);

      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
      }
      session.setMaxInactiveInterval(1200);
      session.setAttribute("entId", accessUserTO.getEnterpriseId());
      return new ResponseEntity<>(new BaseResponseRTO(accessUserTO.getEnterpriseId(), null), HttpStatus.OK);		
   }	

   
   /**
    * Recover password.
    *
    * @param recoverPasswordTO the recover user password TO
    * @param session the session
    * @return the response entity
    */
   @PostMapping("/recover-pwd")
   public @ResponseBody ResponseEntity<Object> recoverPasswordInterviewer(@RequestBody @ModelAttribute RecoverPasswordTO recoverPasswordTO, HttpSession session) {		
      // ErrorRTO errorRTO =
      // checkErrorsRecoverPasswordInterviewer.validate(recoverPasswordTO);

      return null;
   }
   
}
