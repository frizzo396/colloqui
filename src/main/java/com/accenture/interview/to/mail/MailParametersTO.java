package com.accenture.interview.to.mail;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class MailParamsTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailParametersTO {
	
	/** The to. */
	private List<String> to;
	
	/** The cc. */
	private List<String> cc;
	
	/** The body params. */
	private List<String> bodyParams;
	
	/** The subject params. */
	private List<String> subjectParams;
	
	/** The link. */
	private String link;

}
