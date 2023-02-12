package com.accenture.interview.facade;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.AvailabilityService;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.general.MailService;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.to.interview.ReassignInterviewTO;
import com.accenture.interview.to.interview.RescheduledAvailabilityTO;
import com.accenture.interview.to.mail.MailParametersTO;
import com.accenture.interview.utils.constants.WebPaths;
import com.accenture.interview.utils.date.DateUtils;
import com.accenture.interview.utils.enums.MailTypeEnum;

/**
 * The Class AvailabilityFacade.
 */
@Component
public class AvailabilityFacade {

   /** The availability service. */
   @Autowired
   private AvailabilityService availabilityService;

   /** The interview service. */
   @Autowired
   private InterviewService interviewService;

   /** The interviewer service. */
   @Autowired
   private InterviewerService interviewerService;

   /** The mail service. */
   @Autowired
   private MailService mailService;

   /**
    * Adds the availabilty interview.
    *
    * @param insertAvailabilityTO the insert availability TO
    * @return the base response RTO
    */
   public BaseResponseRTO addAvailabiltyInterview(InsertAvailabilityTO insertAvailabilityTO) {
      String errorMsg = null;
      InterviewRTO interview = interviewService.findInterviewWithMailParams(insertAvailabilityTO.getInterviewId());		
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

      LocalDateTime firstLocalDate = LocalDateTime.parse(insertAvailabilityTO.getFirstDate(), formatter);
      LocalDateTime secondLocalDate = LocalDateTime.parse(insertAvailabilityTO.getSecondDate(), formatter);
      LocalDateTime thirdLocalDate = LocalDateTime.parse(insertAvailabilityTO.getThirdDate(), formatter);
      Date firstDate = Date.from(firstLocalDate.atZone(ZoneId.systemDefault()).toInstant());
      Date secondDate = Date.from(secondLocalDate.atZone(ZoneId.systemDefault()).toInstant());
      Date thirdDate = Date.from(thirdLocalDate.atZone(ZoneId.systemDefault()).toInstant());

      availabilityService.addAvailabiltyInterview(insertAvailabilityTO, firstDate, secondDate, thirdDate);

      MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()),
            Arrays.asList(interview.getAssignerMail()),
            Arrays.asList(interview.getCandidateName(),
                  interview.getCandidateSurname(),
                  insertAvailabilityTO.getFirstDate(),
                  insertAvailabilityTO.getSecondDate(),
                  insertAvailabilityTO.getThirdDate()),
            Arrays.asList(interview.getCandidateName(),
                  interview.getCandidateSurname()),
            WebPaths.ASSIGNED);

      mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_INSERT);
      return new BaseResponseRTO(insertAvailabilityTO.getInterviewId(), errorMsg);
   }

   /**
    * Approve availability.
    *
    * @param approveAvailabilityTO the approve availability TO
    * @return the base response RTO
    */
   public BaseResponseRTO approveAvailability(ApproveAvailabilityTO approveAvailabilityTO) {
      String errorMsg = null;
      InterviewRTO interview = interviewService.findInterviewWithMailParams(approveAvailabilityTO.getInterviewId());
      if (!ObjectUtils.isEmpty(interview)) {
         boolean isReschedule = isReschedule(approveAvailabilityTO);
         Date approvedDate = isReschedule ? null : DateUtils.createDateFromString(approveAvailabilityTO.getApprovedDate(), "MMM d, yyyy, h:mm:ss a", Locale.ENGLISH);
         String dateString = isReschedule ? approveAvailabilityTO.getNewDate() : approvedDate.toString();

         availabilityService.approveAvailabilty(approveAvailabilityTO.getInterviewId(), approvedDate, approveAvailabilityTO.getNewDate(), isReschedule);
         MailTypeEnum mailType = isReschedule ? MailTypeEnum.AVAILABILITY_RESCHEDULE: MailTypeEnum.AVAILABILITY_APPROVE;
         MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()),
               Arrays.asList(interview.getAssignerMail()),
               Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), dateString),
               Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()),
               WebPaths.IN_PROGRESS);
         mailService.sendMail(mailParams, mailType);
      }
      return new BaseResponseRTO(approveAvailabilityTO.getInterviewId(), errorMsg);
   }
   /*
    * String webLink =
    * eventService.createTeamsMeeting(approveAvailabilityTO.
    * getApprovedDate()); String body =
    * messageSource.getMessage("teams.event.body", null,
    * Locale.getDefault()).replace("$link", webLink); String
    * candidate = interview.getCandidateName() + " " +
    * interview.getCandidateSurname();
    * mailService.sendCalendarMail(new CalendarTO.Builder()
    * .withSubject("Colloquio " + candidate)
    * .withFrom(interview.getInterviewerMail())
    * .withCc(interview.getAssignerMail()) .withBody(body)
    * .withToEmail(interview.getCandidateMail())
    * .withMeetingStartTime(approveAvailabilityTO.
    * getApprovedDate().toInstant().atZone(ZoneId.systemDefault
    * ()).toLocalDateTime())
    * .withMeetingEndTime(approveAvailabilityTO.getApprovedDate
    * ().toInstant().atZone(ZoneId.systemDefault()).
    * toLocalDateTime().plusHours(1)) .build());
    * 
    * if(ObjectUtils.isEmpty(webLink)) { errorMsg =
    * eventService.eventNotSendErrorMessage(); }
    */



   /**
    * Refuse availability.
    *
    * @param interviewId the interview id
    * @return the base response RTO
    */
   public BaseResponseRTO refuseAvailability(Long interviewId) {
      String errorMsg = null;
      InterviewRTO interview = interviewService.findInterviewWithMailParams(interviewId);
      Long refuseId = interviewService.refuseInterview(interviewId);
      availabilityService.clearAvailabilities(interviewId);
      MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getInterviewerMail()),
            Arrays.asList(interview.getAssignerMail()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()),
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()),
            WebPaths.ASSIGNED);
      mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_REFUSE);
      return new BaseResponseRTO(refuseId, errorMsg);
   }

   /**
    * Reassign availability.
    *
    * @param reassignTO the reassign TO
    * @return the base response RTO
    */
   public BaseResponseRTO reassignAvailability(ReassignInterviewTO reassignTO) {
      String errorMsg = null;
      InterviewerRTO newInterviewer = interviewerService.findInterviewerByEnterpriseId(reassignTO.getEnterpriseId());
      InterviewRTO interview = interviewService.findInterviewToReassign(reassignTO.getInterviewId());
      Long reassignedInterview = interviewService.reassignInterview(reassignTO.getInterviewId(), newInterviewer);
      List<String> responsibleMails = interviewerService.getAllResponsibles().stream().map(InterviewerRTO::getMail).collect(Collectors.toList());
      List<String> bodyParams = !ObjectUtils.isEmpty(interview.getNote()) ? Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname(), interview.getNote())
            : Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname());
      MailTypeEnum mailType = !ObjectUtils.isEmpty(interview.getNote()) ? MailTypeEnum.INTERVIEW_INSERT : MailTypeEnum.INTERVIEW_INSERT_WITHOUT_NOTES;
      MailParametersTO mailParams = new MailParametersTO(Arrays.asList(newInterviewer.getMail()),
            responsibleMails,
            bodyParams,
            Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), WebPaths.IN_PROGRESS);

      mailService.sendMail(mailParams, mailType);
      return new BaseResponseRTO(reassignedInterview, errorMsg);
   }

   /**
    * Checks if is reschedule.
    *
    * @param approveAvailabilityTO the approve availability TO
    * @return true, if is reschedule
    */
   private boolean isReschedule(ApproveAvailabilityTO approveAvailabilityTO) {
      return ObjectUtils.isEmpty(approveAvailabilityTO.getApprovedDate())
            && !ObjectUtils.isEmpty(approveAvailabilityTO.getNewDate());
   }

   /**
    * Accept rescheduled.
    *
    * @param rescheduleTO the reschedule TO
    * @return the base response RTO
    */
   public BaseResponseRTO acceptRescheduled(RescheduledAvailabilityTO rescheduleTO) {
      String errorMsg = null;
      InterviewRTO interview = interviewService.findInterviewWithMailParams(rescheduleTO.getInterviewId());
      if (!ObjectUtils.isEmpty(interview)) {
         LocalDateTime newLocalDate = LocalDateTime.parse(rescheduleTO.getNewDate(), new DateTimeFormatterBuilder()
               .parseCaseInsensitive()
               .appendPattern("MM-dd-yyyy HH:mm").toFormatter());
         String formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(newLocalDate);
         interviewService.acceptRescheduled(rescheduleTO.getInterviewId());
         MailParametersTO mailParams = new MailParametersTO(Arrays.asList(interview.getAssignerMail()),
               Arrays.asList(interview.getInterviewerMail()),
               Arrays.asList(formattedDate, interview.getCandidateName(), interview.getCandidateSurname()),
               Arrays.asList(interview.getCandidateName(), interview.getCandidateSurname()), WebPaths.IN_PROGRESS);
         mailService.sendMail(mailParams, MailTypeEnum.AVAILABILITY_RESCHEDULE_ACCEPTED);
      }
      return new BaseResponseRTO(interview.getId(), errorMsg);
   }
}
