package com.accenture.interview.service.general;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.accenture.interview.to.mail.CalendarTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.enums.MailTypeEnum;
import com.accenture.interview.utils.mail.MailUtils;

/**
 * The Class MailService.
 */
@Service
public class MailService {

   /** The Constant LOGGER. */
   private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

   /** The from. */
   @Value("${aws.ses.from}")
	private String from;

   /** The host. */
   @Value("${aws.ses.host}")
   private String host;

   /** The user. */
   @Value("${aws.ses.user}")
   private String user;

   /** The password. */
   @Value("${aws.ses.password}")
   private String password;

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
   @Async
   public void sendMail(MailParametersTO mailParams, MailTypeEnum type) {
      LOGGER.info("[{}] sendMail", Thread.currentThread().getName());
		try {
		  JavaMailSenderImpl impl = (JavaMailSenderImpl) mailSender;
			MimeMessage message = mailSender.createMimeMessage();
			message.setSubject(mailUtils.getMailSubject(mailParams.getSubjectParams(), type));
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setCc(mailParams.getCc().toArray(new String[0])); 
			helper.setTo(mailParams.getTo().toArray(new String[0]));
			helper.setText(mailUtils.getMailBody(mailParams.getBodyParams(), mailParams.getLink(), type), true);
         Thread.sleep(40000);
         Session session = Session.getDefaultInstance(impl.getJavaMailProperties());
         Transport transport = session.getTransport();
         transport.connect(host, user, password);
         transport.sendMessage(message, message.getAllRecipients());
         // transport.close();
      } catch (Exception e) {
         e.printStackTrace();
		}
	}	

	/**
	 * Send calendar mail.
	 *
	 * @param calendarRequest the calendar request
	 * @return true, if successful
	 */
	public boolean sendCalendarMail(CalendarTO calendarRequest){
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessage.addHeaderLine("method=REQUEST");
			mimeMessage.addHeaderLine("charset=UTF-8");
			mimeMessage.addHeaderLine("component=VEVENT");
			mimeMessage.setFrom(new InternetAddress(calendarRequest.getFrom()));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getToEmail()));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(calendarRequest.getFrom()));
			mimeMessage.setSubject(calendarRequest.getSubject());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
			StringBuilder builder = new StringBuilder();
			builder.append("BEGIN:VCALENDAR\n" +
					"METHOD:REQUEST\n" +
					"PRODID:Microsoft Exchange Server 2010\n" +
					"VERSION:2.0\n" +
					"BEGIN:VTIMEZONE\n" +
					"TZID:Europe/Berlin\n" +
					"END:VTIMEZONE\n" +
					"BEGIN:VEVENT\n" +
					"ATTENDEE;ROLE=REQ-PARTICIPANT;CN=:MAILTO:" + calendarRequest.getToEmail() + " \n" +
					"ATTENDEE;ROLE=REQ-PARTICIPANT;CN=:MAILTO:" + calendarRequest.getFrom() + " \n" +
					"ATTENDEE;ROLE=OPT-PARTICIPANT;CN=:MAILTO:" + calendarRequest.getCc() + " \n" +
					"ORGANIZER;CN=:MAILTO:\n" +
					"DESCRIPTION;LANGUAGE=en-US:\n" +
					"UID:"+calendarRequest.getUid()+"\n" +
					"SUMMARY;LANGUAGE=en-US:Colloquio Accenture\n" +
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

			MimeBodyPart calendarPart = new MimeBodyPart();
			calendarPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
			calendarPart.setHeader("Content-ID", "calendar_message");
			calendarPart.setDataHandler(new DataHandler(
					new ByteArrayDataSource(builder.toString(), "text/calendar;method=REQUEST;name=\"invite.ics\"")));

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(calendarRequest.getBody(),"text/html");


			MimeMultipart multipart = new MimeMultipart();		
			multipart.addBodyPart(htmlPart);
			multipart.addBodyPart(calendarPart);
			mimeMessage.setContent(multipart); 
			mailSender.send(mimeMessage);
			return true;
		} catch (MessagingException | IOException e) {
			return false;
		}
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
