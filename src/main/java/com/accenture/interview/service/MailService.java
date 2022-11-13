package com.accenture.interview.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The Class EmailService.
 */
@Service
public class MailService {

	/** The mail sender. */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Send mail.
	 *
	 * @param from the from
	 * @param to the to
	 * @param cc the cc
	 * @param subject the subject
	 * @param body the body
	 * @return true, if successful
	 */
	public boolean sendMail(String from, String to, String cc, String subject, String body) {		
		try {
			JavaMailSenderImpl mailImpl = (JavaMailSenderImpl) mailSender;				
			mailImpl.testConnection();

			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setCc(cc);
			helper.setTo(to);
			helper.setText(body, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}	
	
	/**
	 * Send mail.
	 *
	 * @param from the from
	 * @param to the to
	 * @param cc the cc
	 * @param subject the subject
	 * @param body the body
	 * @return true, if successful
	 */
	public boolean sendMail(String from, List<String> to, String cc, String subject, String body) {		
		try {
			JavaMailSenderImpl mailImpl = (JavaMailSenderImpl) mailSender;				
			mailImpl.testConnection();
			String[] toArray = to.toArray(new String[0]);

			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setCc(cc);
			helper.setTo(toArray);
			helper.setText(body, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}	
}
