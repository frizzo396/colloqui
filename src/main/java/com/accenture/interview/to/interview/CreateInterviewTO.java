package com.accenture.interview.to.interview;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.accenture.interview.entity.Site;

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
	
    @NotNull(message = "interview.site.notempty")
	private Site site;

	/** The candidate name. */
	@NotEmpty(message = "candidate.name.notempty")
	private String candidateName;

	/** The candidate surname. */
	@NotEmpty(message = "candidate.surname.notempty")
	private String candidateSurname;

	/** The candidate birth. */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date candidateBirth;

	/** The mail. */
	@Email(message = "candidate.mail.invalid")
	@NotEmpty(message = "candidate.mail.notempty")
	private String mail;

	/** The cv. */
	private String cv;

	/** The edu qualification. */
	@NotEmpty(message = "candidate.qualification.notempty")
	private String eduQualification;

	/** The candidate type. */
	@NotEmpty(message = "candidate.type.notempty")
	private String candidateType;

	/** The interview type. */
	@NotEmpty(message = "interview.type.notempty")
	private String interviewType;

	/** The enterprise id. */
	@NotEmpty(message = "interviewer.enterpriseid.notempty")
	private String enterpriseId;

	/** The scheduled date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull(message = "interview.scheduleddate.notempty")
	private Date scheduledDate;

}
