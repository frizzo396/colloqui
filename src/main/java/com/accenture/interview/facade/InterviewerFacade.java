package com.accenture.interview.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interviewer.ChangePasswordInterviewerTO;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.enums.MailTypeEnum;

/**
 * The Class InterviewerFacade.
 */
@Component
public class InterviewerFacade {

	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;

	/** The mail service. */
	@Autowired
	private MailService mailService;

	/** The message source. */	
	@Autowired 
	private MessageSource messageSource;

	/**
    * Adds the new interviewer.
    *
    * @param request the request
    * @return the base response RTO
    */
	public BaseResponseRTO addNewInterviewer(RegisterInterviewerTO request) {
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseIdOrMail(request.getEnterpriseId(), request.getMail());	

		if(!(ObjectUtils.isEmpty(interviewer)) 
			&& !(isEnterpriseidAndMailEqual(request, interviewer.getEnterpriseId(), interviewer.getMail()))){
			return new BaseResponseRTO(interviewer, messageSource.getMessage("interviewer.error.already-register", null, Locale.getDefault()));
		}
		// QUANDO VIENE INSERITO UN NUOVO UTENTE, STATUS = 1
		request.setStatus(1);	
		
		String password = interviewerService.addNewInterviewer(request);
		ArrayList<String> bodyParams = new ArrayList<>();
		if(!ObjectUtils.isEmpty(password)) {
			bodyParams.add(password);
		}
		MailParametersTO mailParams = new MailParametersTO(Arrays.asList(request.getMail()), 
				new ArrayList<>(), bodyParams, new ArrayList<>(), WebPaths.HOME);
		
		boolean result = mailService.sendMail(mailParams, MailTypeEnum.USER_WELCOME);	
		if(!result) {
			return new BaseResponseRTO(null, mailService.registrationRequestMailNotSend());
		}
		return new BaseResponseRTO(new InterviewerRTO(request), null);
	}
	
	/**
    * Modify interviewer.
    *
    * @param request the request
    * @return the base response RTO
    */
	public BaseResponseRTO modifyInterviewer(ModifyInterviewerTO request) {
		interviewerService.modifyInterviewer(request);		
		return new BaseResponseRTO(new InterviewerRTO(request), null);
	}
	
	/**
    * Change password interviewer.
    *
    * @param request the request
    * @return the base response RTO
    */
	public BaseResponseRTO changePasswordInterviewer(ChangePasswordInterviewerTO request) {
      return interviewerService.changePasswordInterviewer(request, messageSource);
	}	
	
	/**
    * Enable disable interviewer.
    *
    * @param request the request
    * @return the base response RTO
    */
	public BaseResponseRTO enableDisableInterviewer(ModifyInterviewerTO request) {
		interviewerService.enableDisableInterviewer(request);		
		return new BaseResponseRTO(new InterviewerRTO(request), null);
	}	
	
	/**
    * Checks if is enterpriseid and mail equal.
    *
    * @param registerTO   the register TO
    * @param enterpriseId the enterprise id
    * @param mail         the mail
    * @return true, if is enterpriseid and mail equal
    */
	private boolean isEnterpriseidAndMailEqual(RegisterInterviewerTO registerTO, String enterpriseId, String mail) {
		return registerTO.getEnterpriseId().equalsIgnoreCase(enterpriseId) && registerTO.getMail().equalsIgnoreCase(mail);
	}

	/**
    * Search interviewer.
    *
    * @param mail the mail
    * @return the interviewer RTO
    */
	public InterviewerRTO searchInterviewer(String mail) {
		InterviewerRTO interviewer = interviewerService.findInterviewerByMail(mail);
		if (!ObjectUtils.isEmpty(interviewer)) {
			return interviewer;
		} else {
			throw new IllegalStateException("Intervistatore non presente.");
		}
	}

	/**
    * Interviewer info.
    *
    * @param enterpriseId the enterprise id
    * @return the interviewer RTO
    */
	public InterviewerRTO interviewerInfo(String enterpriseId) {
		return interviewerService.findInterviewerByEnterpriseId(enterpriseId);
	}

	
	/**
	 * Request registration.
	 *
	 * @param requestTO the request TO
	 * @return the base response RTO
	 */
	public BaseResponseRTO requestRegistration(RequestRegistrationTO requestTO) {
		List<InterviewerRTO> allResponsibles = interviewerService.getAllResponsibles();		
		MailParametersTO mailParams = new MailParametersTO(allResponsibles.stream().map(InterviewerRTO::getMail).collect(Collectors.toList()), 
				Arrays.asList(requestTO.getMail()), 
				Arrays.asList(requestTO.getEnterpriseId(), requestTO.getMail()),
				Arrays.asList(requestTO.getEnterpriseId()), null);
		
		boolean result = mailService.sendMail(mailParams, MailTypeEnum.USER_REGISTER);	
		if(!result) {
			return new BaseResponseRTO(null, mailService.registrationRequestMailNotSend());
		}
		return new BaseResponseRTO(requestTO.getEnterpriseId(), null);
	}
	
	
	/**
	 * Find all interviewers.
	 *
	 * @return the list
	 */
	public List<InterviewerRTO> findAllInterviewers(){		
		return interviewerService.findAllInterviewers();
	}
	
	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	public List<InterviewerRTO> findAllUsers(){		
		return interviewerService.findAllUsers();
	}
	
	
	/**
    * Recover password.
    *
    * @param enterpriseId the enterprise id
    * @return the string
    */
   public String recoverPassword(String enterpriseId) {
      String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
      String password = RandomStringUtils.random(8, characters);
      String mail = interviewerService.recoverPassword(enterpriseId, password);

      MailParametersTO mailParams = new MailParametersTO(Arrays.asList(mail),
            new ArrayList<>(), Arrays.asList(password), new ArrayList<>(), WebPaths.HOME);
      mailService.sendMail(mailParams, MailTypeEnum.USER_RECOVER_PASSWORD);
      return enterpriseId;
	}	

   /**
    * Login.
    *
    * @param enterpriseId the enterprise id
    * @return the interviewer RTO
    */
   public InterviewerRTO login(String enterpriseId) {
      return interviewerService.findInterviewerByEnterpriseId(enterpriseId);
   }

}
