package com.accenture.interview.utils.mail;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.utils.enums.MailTypeEnum;

/**
 * The Class MailUtils.
 */
@Component
public class MailUtils {

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Gets the mail body.
	 *
	 * @param bodyParams the body params
	 * @param link the link
	 * @param mailType the mail type
	 * @return the mail body
	 */
	public String getMailBody(List<String> bodyParams, String link, MailTypeEnum mailType) {
		String body = messageSource.getMessage(mailType.getValue(), null, Locale.getDefault());

		if(!ObjectUtils.isEmpty(body)) {
			for(int i = 0; i < bodyParams.size(); i++) {
				if(body.contains("$"+i)) {
					body = body.replace("$"+i, bodyParams.get(i));
				}
			}
			if(!ObjectUtils.isEmpty(link)) {
				body = body.replace("$link", link);
			}
		}

		return body;
	}


	/**
	 * Gets the mail subject.
	 *
	 * @param subjectParams the subject params
	 * @param mailType the mail type
	 * @return the mail subject
	 */
	public String getMailSubject(List<String> subjectParams, MailTypeEnum mailType) {
		String subject = null;

		if(mailType.equals(MailTypeEnum.AVAILABILITY_APPROVE) || mailType.equals(MailTypeEnum.AVAILABILITY_INSERT) ||
				mailType.equals(MailTypeEnum.FEEDBACK_INSERT) || mailType.equals(MailTypeEnum.INTERVIEW_INSERT)
				|| mailType.equals(MailTypeEnum.AVAILABILITY_REFUSE)) {
			subject = messageSource.getMessage("mail.subject.interview", null, Locale.getDefault());			
		} 
		else if(mailType.equals(MailTypeEnum.USER_REGISTER)) {
			subject = messageSource.getMessage("mail.subject.registration", null, Locale.getDefault());
		}
		else if(mailType.equals(MailTypeEnum.USER_WELCOME)) {
			subject = messageSource.getMessage("mail.subject.welcome", null, Locale.getDefault());
		}

		if(!ObjectUtils.isEmpty(subject)) {	
			for(int i = 0; i < subjectParams.size(); i++) {
				if(subject.contains("$"+i)) {
					subject = subject.replace("$"+i, subjectParams.get(i));
				}
			}
		}
		return subject;
	}


	/**
	 * Mail not send.
	 *
	 * @return the string
	 */
	public String mailNotSend() {
		return messageSource.getMessage("mail.notsend", null, Locale.getDefault());
	}

	/**
	 * Registration request mail not send.
	 *
	 * @return the string
	 */
	public String registrationRequestMailNotSend() {
		return messageSource.getMessage("interviewer.req.registration.mail.notsend", null, Locale.getDefault());
	}

}
