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

/**
 * The Class Interviewer.
 */
@Entity(name = "Interviewer")
@Table(name = "interviewer")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Interview> getInterviewList() {
		return interviewList;
	}

	public void setInterviewList(List<Interview> interviewList) {
		this.interviewList = interviewList;
	}

}
