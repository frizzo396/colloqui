package com.accenture.interview.service.general;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.enums.MailTypeEnum;
import com.accenture.interview.utils.mail.MailUtils;

/**
 * The Class EmailService.
 */
@Service
public class MailService {
	
    /** The user. */
    @Value("${spring.mail.username}")
    private String from;

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;
	
	/** The mail utils. */
	@Autowired
	private MailUtils mailUtils;
	

	/**
	 * Send mail.
	 *
	 * @param mailParams the mail params
	 * @param type the type
	 * @return true, if successful
	 */
	public boolean sendMail(MailParametersTO mailParams, MailTypeEnum type) {		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(mailUtils.getMailSubject(mailParams.getSubjectParams(), type));
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setCc(mailParams.getCc().toArray(new String[0])); 
			helper.setTo(mailParams.getTo().toArray(new String[0]));
			helper.setText(mailUtils.getMailBody(mailParams.getBodyParams(), mailParams.getLink(), type), true);
			mailSender.send(message);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}	
	
	/**
	 * Mail not send.
	 *
	 * @return the string
	 */
	public String mailNotSend() {
		return mailUtils.mailNotSend();
	}
	
	/**
	 * Registration request mail not send.
	 *
	 * @return the string
	 */
	public String registrationRequestMailNotSend() {
		return mailUtils.registrationRequestMailNotSend();
	}

}
