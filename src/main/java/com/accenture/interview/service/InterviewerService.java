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
import com.accenture.interview.utils.checkerror.interviewer.CheckNewPassword;

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
		
		//Update flag
		if(optInterviewer.isPresent()) {
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
		Interviewer interviewer = null;
		Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());
		
		//Update flag
		if(optInterviewer.isPresent()) {
			interviewer = optInterviewer.get();
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
		Interviewer interviewer = null;
		Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(request.getEnterpriseId());		
		
		BaseResponseRTO resRTO;
		resRTO = null;
		
		if(optInterviewer.isPresent()) {
			interviewer = optInterviewer.get();
						
			// password fornita nella popup modale
			String formOldPassword = request.getOldPassword();			
			// password presente nel database
			String dbCurrentPassword = interviewer.getPassword();
			
			// CONTROLLO DELLA VECCHIA PASSWORD
			if (dbCurrentPassword.equals(formOldPassword)) {
				
				// PROSEGUO CON LA VERIFICA DELLA NUOVA PASSWORD				
				String newPassword = request.getNewPassword();
				 
				if (CheckNewPassword.checkNewPassword(newPassword)) {					
					interviewer.setPassword(newPassword);
					interviewerRepository.save(interviewer);					
					resRTO = new BaseResponseRTO(new InterviewerRTO(request), null);
				} else {
					resRTO = new BaseResponseRTO(interviewer, messageSource.getMessage("interviewer.change-pwd.error.password-not-valid", null, Locale.getDefault()));					
				}									
			} else {
				resRTO = new BaseResponseRTO(interviewer, messageSource.getMessage("interviewer.change-pwd.error.password-not-present", null, Locale.getDefault()));
			}
		}		
		return resRTO;
	}	
	
	/**
    * Enable disable interviewer.
    *
    * @param request the request
    */
	public void enableDisableInterviewer(ModifyInterviewerTO request) {
		Interviewer interviewer = null;
		Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityById(request.getId());
		
		// Update status
		if(optInterviewer.isPresent()) {
			interviewer = optInterviewer.get();
			
			// enable status
			if (interviewer.getStatus() == 0) {
				interviewer.setStatus(1);
			} else {
				// disable status
				if (interviewer.getStatus() == 1) {
					interviewer.setStatus(0);
				}					
			}
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
	 * @param mail the mail
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
	public List<InterviewerRTO> findAllInterviewers(){		
		return interviewerRepository.findAllInterviewers();
	}
	
	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	public List<InterviewerRTO> findAllUsers(){		
		return interviewerRepository.findAllUsers();
	}	

   /**
    * Recover password.
    *
    * @param userId   the user id
    * @param password the password
    */
   public void recoverPassword(String enterpriseId, String password) {
      InterviewerRTO interviewerRTO = interviewerRepository.findInterviewerByEnterpriseId(enterpriseId);
      Optional<Interviewer> optInterview = interviewerRepository.findById(interviewerRTO.getId());
      if (optInterview.isPresent()) {
         Interviewer interviewer = optInterview.get();
         interviewer.setPassword(password);
         interviewerRepository.save(interviewer);
      }
   }

}
