package com.accenture.interview.rto.interview;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class UpdateInterviewRTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterviewRTO {

   /** The interview id. */
   private Long interviewId;

   private String site;

   /** The candidate name. */
   private String candidateName;

   /** The candidate surname. */
   private String candidateSurname;

   /** The candidate birth. */
   @JsonFormat(pattern = "MM-dd-YYYY HH:mm", timezone = "Europe/Rome")
   private Date candidateBirth;

   /** The mail. */
   private String mail;

   /** The edu qualification. */
   private String eduQualification;

   /** The candidate type. */
   private String candidateType;

   /** The interview type. */
   private long interviewType;

   /** The enterprise id. */
   private String enterpriseId;

   /** The note. */
   private String note;

   private String interviewTypeString;

   public UpdateInterviewRTO(Long interviewId, String site, String candidateName, String candidateSurname, Date candidateBirth, String mail, String eduQualification, String candidateType,
         long interviewType, String note) {
      super();
      this.interviewId = interviewId;
      this.site = site;
      this.candidateName = candidateName;
      this.candidateSurname = candidateSurname;
      this.candidateBirth = candidateBirth;
      this.mail = mail;
      this.eduQualification = eduQualification;
      this.candidateType = candidateType;
      this.interviewType = interviewType;
      this.note = note;
   }

}
