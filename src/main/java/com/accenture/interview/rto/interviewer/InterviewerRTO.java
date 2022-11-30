package com.accenture.interview.rto.interviewer;

import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.to.interviewer.ModifyInterviewerTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class InterviewerRTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewerRTO {

	/** The id. */
	private long id;

	/** The enterprise id. */
	private String enterpriseId;

	/** The mail. */
	private String mail;
	
	/** The type. */
	private long type;
	
	/** The status: 1 (attivo), 0 (inattivo/eliminato) */
	private long status;
	
	
	public InterviewerRTO(long id, String enterpriseId, String mail, long type) {
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.mail = mail;
		this.type = type;
	}	

	/**
	 * Instantiates a new search interviewer response.
	 *
	 * @param interviewer the interviewer
	 */
	public InterviewerRTO(Interviewer interviewer) {
		this.id = interviewer.getId();
		this.enterpriseId = interviewer.getEnterpriseId();
		this.mail = interviewer.getMail();		
		this.type = interviewer.getType();		
		this.status = interviewer.getStatus();
	}
	
	/**
	 * Instantiates a new interviewer.
	 *
	 * @param request the register interviewer request
	 */
	public InterviewerRTO(RegisterInterviewerTO request) {		
		this.enterpriseId = request.getEnterpriseId();
		this.mail = request.getMail();
		this.type = request.getIsResponsible().longValue();
	}
	
	/**
	 * Instantiates interviewer.
	 *
	 * @param request the modified interviewer request
	 */
	public InterviewerRTO(ModifyInterviewerTO request) {
		this.id = request.getId();		
		this.enterpriseId = request.getEnterpriseId();
		
		if (request.getIsResponsible() != null) {
			this.type = request.getIsResponsible().longValue();
		}
	}	

}
