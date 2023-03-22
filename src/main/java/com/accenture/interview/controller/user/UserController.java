package com.accenture.interview.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsRecoverPasswordInterviewer;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsRegisterInterviewer;

/**
 * The Class UserController.
 */
@RestController
@RequestMapping("/user")
public class UserController {

   /** The interviewer facade. */
   @Autowired
   private InterviewerFacade interviewerFacade;

   /** The check errors register interviewer. */
   @Autowired
   private CheckErrorsRegisterInterviewer checkErrorsRegisterInterviewer;


   /** The check errors recover password interviewer. */
   @Autowired
   private CheckErrorsRecoverPasswordInterviewer checkErrorsRecoverPasswordInterviewer;


   /**
    * Register interviewer.
    *
    * @param registerUserTO the register user TO
    * @return the response entity
    */
   @PostMapping("/register")
   public ResponseEntity<Object> registerInterviewer(@RequestBody RegisterInterviewerTO registerUserTO) {
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(registerUserTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewerFacade.addNewInterviewer(registerUserTO), HttpStatus.OK);
   }

   /**
    * Modify interviewer.
    *
    * @param modifyUserTO the modify user TO
    * @return the response entity
    */
   @PutMapping("/edit")
   public ResponseEntity<Object> modifyInterviewer(@RequestBody ModifyInterviewerTO modifyUserTO) {
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(modifyUserTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewerFacade.modifyInterviewer(modifyUserTO), HttpStatus.OK);
   }

   /**
    * Change password interviewer.
    *
    * @param changePasswordInterviewerTO the change password
    *                                    interviewer TO
    * @return the response entity
    */
   @PutMapping("/change-pwd")
   public ResponseEntity<Object> changePasswordInterviewer(@RequestBody ChangePasswordInterviewerTO changePasswordInterviewerTO) {
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(changePasswordInterviewerTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewerFacade.changePasswordInterviewer(changePasswordInterviewerTO), HttpStatus.OK);
   }

   /**
    * Enable disable interviewer.
    *
    * @param id the id
    * @return the response entity
    */
   @PutMapping("/enable-disable")
   public ResponseEntity<Object> enableDisableInterviewer(@RequestParam("userIdParam") Long id) {
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
   public ResponseEntity<Object> requestRegistration(@RequestBody RequestRegistrationTO requestTO) {
      ErrorRTO errorRTO = checkErrorsRegisterInterviewer.validate(requestTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewerFacade.requestRegistration(requestTO), HttpStatus.OK);
   }


   /**
    * Recover password interviewer.
    *
    * @param enterpriseId the enterprise id
    * @return the response entity
    */
   @PostMapping("/recover-pwd")
   public @ResponseBody ResponseEntity<Object> recoverPasswordInterviewer(@RequestParam(name = "enterpriseId") String enterpriseId) {
      ErrorRTO errorRTO = checkErrorsRecoverPasswordInterviewer.validate(enterpriseId);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      return new ResponseEntity<>(interviewerFacade.recoverPassword(enterpriseId), HttpStatus.OK);
   }
}
