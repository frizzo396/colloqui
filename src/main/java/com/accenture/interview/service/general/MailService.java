package com.accenture.interview.service.general;

import java.time.format.DateTimeFormatter;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.accenture.interview.to.mail.CalendarTO;
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
	
	public void sendCalendarInvite(String fromEmail, CalendarTO calendarRequest) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.addHeaderLine("method=REQUEST");
        mimeMessage.addHeaderLine("charset=UTF-8");
        mimeMessage.addHeaderLine("component=VEVENT");
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
        mimeMessage.setSubject(calendarRequest.getSubject());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        StringBuilder builder = new StringBuilder();
        builder.append("BEGIN:VCALENDAR\n" +
                "METHOD:REQUEST\n" +
                "PRODID:Microsoft Exchange Server 2010\n" +
                "VERSION:2.0\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Asia/Kolkata\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + calendarRequest.getToEmail() + "\n" +
                "ORGANIZER;CN=Foo:MAILTO:" + fromEmail + "\n" +
                "DESCRIPTION;LANGUAGE=en-US:" + calendarRequest.getBody() + "\n" +
                "UID:"+calendarRequest.getUid()+"\n" +
                "SUMMARY;LANGUAGE=en-US:Discussion\n" +
                "DTSTART:" + formatter.format(calendarRequest.getMeetingStartTime()).replace(" ", "T") + "\n" +
                "DTEND:" + formatter.format(calendarRequest.getMeetingEndTime()).replace(" ", "T") + "\n" +
                "CLASS:PUBLIC\n" +
                "PRIORITY:5\n" +
                "DTSTAMP:20200922T105302Z\n" +
                "TRANSP:OPAQUE\n" +
                "STATUS:CONFIRMED\n" +
                "SEQUENCE:$sequenceNumber\n" +
                "LOCATION;LANGUAGE=en-US:Microsoft Teams Meeting\n" +
                "BEGIN:VALARM\n" +
                "DESCRIPTION:REMINDER\n" +
                "TRIGGER;RELATED=START:-PT15M\n" +
                "ACTION:DISPLAY\n" +
                "END:VALARM\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR");
 
        MimeBodyPart messageBodyPart = new MimeBodyPart();
 
        messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
        messageBodyPart.setHeader("Content-ID", "calendar_message");
        messageBodyPart.setDataHandler(new DataHandler(
                new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));
 
        MimeMultipart multipart = new MimeMultipart();
 
        multipart.addBodyPart(messageBodyPart);
 
        mimeMessage.setContent(multipart);
 
        System.out.println(builder.toString());
 
        mailSender.send(mimeMessage);
        System.out.println("Calendar invite sent");
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
