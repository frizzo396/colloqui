package com.accenture.interview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class TechFeedback.
 */
@Entity(name = "Tech_feedback")
@Table(name = "tech_feedback")
public class TechnicalFeedback {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The java. */
	private long java;

	/** The sql. */
	@Column(name = "sql_voto")
	private int sql;

	/** The html css. */
	@Column(name = "html_css")
	private int htmlCss;

	/** The angular. */
	private int angular;

	/** The spring. */
	private int spring;

	/** The other. */
	private int other;

	/** The comment. */
	private String comment;

	/** The interview. */
	@OneToOne(mappedBy = "techFeedbackId")
	@JsonIgnore
	private Interview interview;

	/**
	 * Instantiates a new tech feedback.
	 */
	public TechnicalFeedback() {
	}

	/**
	 * Instantiates a new tech feedback.
	 *
	 * @param createTechFeedbackRequest the create tech feedback
	 *                                  request
	 */
	public TechnicalFeedback(CreateTechFeedbackTO createTechFeedbackRequest) {
		this.java = createTechFeedbackRequest.getJava();
		this.sql = createTechFeedbackRequest.getSqlVoto();
		this.htmlCss = createTechFeedbackRequest.getHtmlCss();
		this.angular = createTechFeedbackRequest.getAngular();
		this.spring = createTechFeedbackRequest.getSpring();
		this.other = createTechFeedbackRequest.getOther();
		this.comment = createTechFeedbackRequest.getComment();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJava() {
		return java;
	}

	public void setJava(long java) {
		this.java = java;
	}

	public int getSql() {
		return sql;
	}

	public void setSql(int sql) {
		this.sql = sql;
	}

	public int getHtmlCss() {
		return htmlCss;
	}

	public void setHtmlCss(int htmlCss) {
		this.htmlCss = htmlCss;
	}

	public int getAngular() {
		return angular;
	}

	public void setAngular(int angular) {
		this.angular = angular;
	}

	public int getSpring() {
		return spring;
	}

	public void setSpring(int spring) {
		this.spring = spring;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}
}
