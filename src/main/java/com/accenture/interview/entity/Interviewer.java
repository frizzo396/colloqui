package com.accenture.interview.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class Interviewer.
 */
@Entity(name = "Interviewer")
@Table(name = "interviewer")
@NoArgsConstructor
public class Interviewer {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The enterprise id. */
	@NotNull
	@Column(name = "enterprise_id")
	private String enterpriseId;

	/** The mail. */
	@NotNull
	private String mail;

	/** The password. */
	@NotNull
	private String password;

	/** The interview list. */
	@OneToMany(mappedBy = "interviewerId")
	@JsonIgnore
	private List<Interview> interviewList;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the enterprise id.
	 *
	 * @return the enterprise id
	 */
	public String getEnterpriseId() {
		return enterpriseId;
	}

	/**
	 * Sets the enterprise id.
	 *
	 * @param enterpriseId the new enterprise id
	 */
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the interview list.
	 *
	 * @return the interview list
	 */
	public List<Interview> getInterviewList() {
		return interviewList;
	}

	/**
	 * Sets the interview list.
	 *
	 * @param interviewList the new interview list
	 */
	public void setInterviewList(List<Interview> interviewList) {
		this.interviewList = interviewList;
	}

	/**
	 * Instantiates a new interviewer.
	 *
	 * @param id the id
	 * @param enterpriseId the enterprise id
	 * @param mail the mail
	 */
	public Interviewer(long id, @NotNull String enterpriseId, @NotNull String mail) {
		super();
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.mail = mail;
	}
	
	
	

}
