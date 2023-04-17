package com.accenture.interview.to.interview;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CreateInterviewRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateInterviewTO {
	
    /** The site. */
    @NotNull(message = "interview.error.site.not-empty")
   private Long site;

	/** The candidate name. */
	@NotEmpty(message = "interview.error.candidate.name.not-empty")
	private String candidateName;

	/** The candidate surname. */
	@NotEmpty(message = "interview.error.candidate.surname.not-empty")
	private String candidateSurname;

	/** The candidate birth. */
	@NotNull(message = "interview.error.candidate.birthday.not-empty")
	@PastOrPresent(message = "interview.error.candidate.birthday.not-future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date candidateBirth;

	/** The mail. */
	@Email(message = "interview.error.candidate.mail.invalid")
	@NotEmpty(message = "interview.error.candidate.mail.not-empty")
	private String mail;

	/** The edu qualification. */
	@NotEmpty(message = "interview.error.candidate.qualification.not-empty")
	private String eduQualification;

	/** The candidate type. */
   @NotNull(message = "interview.error.candidate.type.not-empty")
   private Long candidateType;

	/** The interview type. */
   @NotNull(message = "interview.error.type.not-empty")
   private Long interviewType;

	/** The enterprise id. */
	private String enterpriseId;
	
	/** The assigner id. */
	private String assignerEnterpriseId;
	
	/** The note. */	
	private String note;
}
