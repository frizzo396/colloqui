package com.accenture.interview.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * The Class EmailService.
 */
@Service
public class EmailService {

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
	 * @throws MessagingException 
	 */
	public void sendMail(String from, String to, String cc, String subject, String body) throws MessagingException 
	{
			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(body, true);
			mailSender.send(message);
	}
}
