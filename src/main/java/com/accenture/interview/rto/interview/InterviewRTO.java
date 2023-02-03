package com.accenture.interview.rto.interview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class InterviewRTO.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterviewRTO {
		
	/** The id. */
	private Long id;
	
	/** The site. */
	private String site;
	
	/** The candidate name. */
	private String candidateName;

	/** The candidate surname. */
	private String candidateSurname;

   /** The candidate mail. */
	private String candidateMail;

	/** The interviewer mail. */
	private String interviewerMail;

	/** The assigner mail. */
	private String assignerMail;

   /** The note. */
   private String note;

   public InterviewRTO(Long id, String site, String candidateName, String candidateSurname, String candidateMail, String interviewerMail, String assignerMail) {
      super();
      this.id = id;
      this.site = site;
      this.candidateName = candidateName;
      this.candidateSurname = candidateSurname;
      this.candidateMail = candidateMail;
      this.interviewerMail = interviewerMail;
      this.assignerMail = assignerMail;
   }

}
