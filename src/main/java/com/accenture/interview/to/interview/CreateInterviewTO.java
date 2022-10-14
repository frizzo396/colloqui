package com.accenture.interview.to.interview;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
	
	/** 2022-10-13 NUOVA COLONNA site - START */
//	@NotEmpty(message = "Il nome della sede di assunzione deve essere valorizzato.")
	private Site site;
	/** 2022-10-13 NUOVA COLONNA site - END */	

	/** The candidate name. */
	@NotEmpty(message = "Il nome del candidato deve essere valorizzato.")
	private String candidateName;

	/** The candidate surname. */
	@NotEmpty(message = "Il nome del candidato deve essere valorizzato.")
	private String candidateSurname;

	/** The candidate birth. */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date candidateBirth;

	/** The mail. */
	@Email(message = "L'email inserita deve essere valida")
	@NotEmpty(message = "L'email del candidato deve essere valorizzato.")
	private String mail;

	/** The cv. */
	private String cv;

	/** The edu qualification. */
	@NotEmpty(message = "La qualifica deve essere valorizzata.")
	private String eduQualification;

	/** The candidate type. */
	@NotEmpty(message = "Il tipo di candidato deve essere specificato.")
	private String candidateType;

	/** The interview type. */
	@NotEmpty(message = "Il tipo di intervista deve essere specificata.")
	private String interviewType;

	/** The enterprise id. */
	@NotEmpty(message = "L'enterpriseId dell'intervistatore deve essere specificato.")
	private String enterpriseId;

	/** The scheduled date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date scheduledDate;

}
