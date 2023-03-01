package com.accenture.interview.utils.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.interview.InterviewRTO;
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

      if (mailType.equals(MailTypeEnum.USER_REGISTER)) {
			subject = messageSource.getMessage("mail.subject.registration", null, Locale.getDefault());
		}
		else if(mailType.equals(MailTypeEnum.USER_WELCOME)) {
			subject = messageSource.getMessage("mail.subject.welcome", null, Locale.getDefault());
		}
		else if(mailType.equals(MailTypeEnum.USER_RECOVER_PASSWORD)) {
			subject = messageSource.getMessage("mail.subject.recover-pwd", null, Locale.getDefault());
      } else if (mailType.equals(MailTypeEnum.INTERVIEW_ASSIGNED) || mailType.equals(MailTypeEnum.INTERVIEW_ASSIGNED_WITH_NOTES)
            || mailType.equals(MailTypeEnum.INTERVIEW_INSERT)
            || mailType.equals(MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES)) {
         subject = messageSource.getMessage("mail.subject.interview.assigned", null, Locale.getDefault());
      } else if (mailType.equals(MailTypeEnum.AVAILABILITY_APPROVE) || mailType.equals(MailTypeEnum.AVAILABILITY_INSERT)
            || mailType.equals(MailTypeEnum.AVAILABILITY_REFUSE) || mailType.equals(MailTypeEnum.AVAILABILITY_RESCHEDULE) || mailType.equals(MailTypeEnum.AVAILABILITY_RESCHEDULE_ACCEPTED)) {
         subject = messageSource.getMessage("mail.subject.interview.availability", null, Locale.getDefault());
      } else if (mailType.equals(MailTypeEnum.FEEDBACK_INSERT)) {
         subject = messageSource.getMessage("mail.subject.interview.feedback", null, Locale.getDefault());
      } else {
         subject = messageSource.getMessage("mail.subject.interview", null, Locale.getDefault());
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

   /**
    * Check body params to assign interview.
    *
    * @param interview     the interview
    * @param interviewDate the interview date
    * @return the list
    */
   public List<String> checkBodyParamsToAssignInterview(InterviewRTO interview, String interviewDate) {
      List<String> bodyParams = new ArrayList<>();
      bodyParams.add(interview.getCandidateName());
      bodyParams.add(interview.getCandidateSurname());

      if (!StringUtils.isBlank(interviewDate)) {
         bodyParams.add(interviewDate);
      }

      if (!ObjectUtils.isEmpty(interview.getNote())) {
         String note = interview.getNote();
         bodyParams.add(note);
      }
      return bodyParams;
   }


   /**
    * Check mail type to assign interview.
    *
    * @param notes the notes
    * @param date  the date
    * @return the mail type enum
    */
   public MailTypeEnum checkMailTypeToAssignInterview(String notes, String date) {

      if (ObjectUtils.isEmpty(notes) && ObjectUtils.isEmpty(date)) {
         return MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES;
      }
      if (ObjectUtils.isEmpty(notes) && !ObjectUtils.isEmpty(date)) {
         return MailTypeEnum.INTERVIEW_ASSIGNED;
      }
      if (!ObjectUtils.isEmpty(notes) && ObjectUtils.isEmpty(date)) {
         return MailTypeEnum.INTERVIEW_INSERT;
      }
      return MailTypeEnum.INTERVIEW_ASSIGNED_WITH_NOTES;
   }

}
