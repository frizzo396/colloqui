package com.accenture.interview.controller.access;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interviewer.AccessUserTO;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsAccessInterviewer;
import com.accenture.interview.utils.checkerror.interviewer.CheckErrorsRecoverPasswordInterviewer;

/**
 * The Class AccessController.
 */
@RestController
@RequestMapping("/access")
public class AccessController {

   /** The check errors access interviewer. */
   @Autowired
   private CheckErrorsAccessInterviewer checkErrorsAccessInterviewer;

   @Autowired
   private CheckErrorsRecoverPasswordInterviewer checkErrorsRecoverPassword;

   @Autowired
   private InterviewerFacade interviewerFacade;


   /**
    * Login.
    *
    * @param accessUserTO the access user TO
    * @param session      the session
    * @return the response entity
    */
   @PostMapping("/login")
   public ResponseEntity<Object> login(@RequestBody AccessUserTO accessUserTO, HttpSession session) {
      ErrorRTO errorRTO = checkErrorsAccessInterviewer.validate(accessUserTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(errorRTO, HttpStatus.UNPROCESSABLE_ENTITY);
      }
      session.setMaxInactiveInterval(1200);
      session.setAttribute("entId", accessUserTO.getEnterpriseId());
      return new ResponseEntity<>(interviewerFacade.login(accessUserTO.getEnterpriseId()), HttpStatus.OK);
   }


   /**
    * Recover password.
    *
    * @param recoverPasswordTO the recover user password TO
    * @param session           the session
    * @return the response entity
    */
   @GetMapping("/recover-pwd")
   public ResponseEntity<Object> recoverPassword(@RequestParam(name = "enterpriseId") String enterpriseId) {
      ErrorRTO errorRTO = checkErrorsRecoverPassword.validate(enterpriseId);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(errorRTO, HttpStatus.UNPROCESSABLE_ENTITY);
      }

      return new ResponseEntity<>(interviewerFacade.recoverPassword(enterpriseId), HttpStatus.OK);
   }

}
