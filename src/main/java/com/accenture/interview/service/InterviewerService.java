package com.accenture.interview.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.repository.interviewer.InterviewerRepository;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.utils.password.PasswordUtils;

/**
 * The Class InterviewerService.
 */
@Service
public class InterviewerService {

   /** The interviewer repository. */
   @Autowired
   private InterviewerRepository interviewerRepository;

   /**
    * Find interviewer by enterprise id.
    *
    * @param enterpriseId the enterprise id
    * @return the interviewer RTO
    */
   public InterviewerRTO findInterviewerByEnterpriseId(String enterpriseId) {
      return interviewerRepository.findInterviewerByEnterpriseId(enterpriseId);
   }

   /**
    * Find interviewer by enterprise id and mail.
    *
    * @param enterpriseId the enterprise id
    * @param email        the email
    * @return the interviewer RTO
    */
   public InterviewerRTO findInterviewerByEnterpriseIdAndMail(String enterpriseId, String email) {
      return interviewerRepository.findInterviewerByEnterpriseIdAndMail(enterpriseId, email);
   }

   /**
    * Adds the new interviewer.
    *
    * @param request the request
    * @return the string
    */
   public String addNewInterviewer(RegisterInterviewerTO request) {
      Interviewer interviewer = null;
      String password = null;
      Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());

      // Update flag
      if (optInterviewer.isPresent()) {
         interviewer = optInterviewer.get();
         interviewer.setType(request.getIsResponsible());
      } else {
         String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
         password = RandomStringUtils.random(8, characters);
         request.setPassword(password);
         interviewer = new Interviewer(request);
      }
      interviewerRepository.save(interviewer);
      return password;
   }

   /**
    * Modify interviewer.
    *
    * @param request the request
    */
   public void modifyInterviewer(ModifyInterviewerTO request) {
      Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());
      if (optInterviewer.isPresent()) {
         Interviewer interviewer = optInterviewer.get();
         interviewer.setType(request.getIsResponsible());
         interviewerRepository.save(interviewer);
      }
   }

   /**
    * Change password interviewer.
    *
    * @param request       the request
    * @param messageSource the message source
    * @return the base response RTO
    */
   public BaseResponseRTO changePasswordInterviewer(ChangePasswordInterviewerTO request, MessageSource messageSource) {
      Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());		

      if(optInterviewer.isPresent()) {
         Interviewer interviewer = optInterviewer.get();
         boolean isPasswordEqual = PasswordUtils.checkPassowordEquality(interviewer.getPassword(), request.getOldPassword());
         if(!isPasswordEqual) {
            return new BaseResponseRTO(interviewer, messageSource.getMessage("interviewer.change-pwd.error.password-not-present", null, Locale.getDefault()));  
         }

         if (PasswordUtils.checkNewPassword(request.getNewPassword())) {					
            interviewer.setPassword(request.getNewPassword());
            interviewerRepository.save(interviewer);					
            return new BaseResponseRTO(new InterviewerRTO(request), null);
         }
         return new BaseResponseRTO(interviewer, messageSource.getMessage("interviewer.change-pwd.error.password-not-valid", null, Locale.getDefault()));
      }
      return null;
   }

   /**
    * Enable disable interviewer.
    *
    * @param request the request
    */
   public void enableDisableInterviewer(ModifyInterviewerTO request) {
      Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityById(request.getId());
      if (optInterviewer.isPresent()) {
         Interviewer interviewer = optInterviewer.get();
         interviewer.setStatus(interviewer.getStatus() == 0 ? 1 : 0);
         interviewerRepository.save(interviewer);
      }
   }

   /**
    * Gets the all responsibles.
    *
    * @return the all responsibles
    */
   public List<InterviewerRTO> getAllResponsibles() {
      return interviewerRepository.findAllResponsibles();
   }

   /**
    * Find interviewer by mail.
    *
    * @param mail the mail
    * @return the interviewer RTO
    */
   public InterviewerRTO findInterviewerByMail(String mail) {
      return interviewerRepository.findInterviewerByMail(mail);
   }

   /**
    * Find interviewer by enterprise id or mail.
    *
    * @param enterpriseId the enterprise id
    * @param mail         the mail
    * @return the interviewer RTO
    */
   public InterviewerRTO findInterviewerByEnterpriseIdOrMail(String enterpriseId, String mail) {
      return interviewerRepository.findInterviewerByEnterpriseIdOrMail(enterpriseId, mail);
   }

   /**
    * Find all interviewers.
    *
    * @return the list
    */
   public List<InterviewerRTO> findAllInterviewers() {
      return interviewerRepository.findAllInterviewers();
   }

   /**
    * Find all users.
    *
    * @return the list
    */
   public List<InterviewerRTO> findAllUsers() {
      return interviewerRepository.findAllUsers();
   }

   /**
    * Recover password.
    *
    * @param enterpriseId the enterprise id
    * @param password     the password
    * @return the string
    */
   public String recoverPassword(String enterpriseId, String password) {
      Optional<Interviewer> optInterview = interviewerRepository.findInterviewerEntityByEnterpriseId(enterpriseId);
      if (optInterview.isPresent()) {
         Interviewer interviewer = optInterview.get();
         interviewer.setPassword(password);
         interviewerRepository.save(interviewer);
         return interviewer.getMail();
      }
      return null;
   }

}
