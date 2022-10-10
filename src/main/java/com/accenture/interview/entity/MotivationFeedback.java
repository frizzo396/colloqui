package com.accenture.interview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class MotivationFeedback.
 */
@Entity(name = "Motivation_feedback")
@Table(name = "motivation_feedback")
public class MotivationFeedback {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The standing. */
	@NotNull
	private int standing;

	/** The school background. */
	@NotNull
	@Column(name = "school_background")
	private int schoolBackground;

	/** The motivation. */
	@NotNull
	private int motivation;

	/** The soft skills. */
	@NotNull
	@Column(name = "soft_skills")
	private int softSkills;

	/** The english level. */
	@NotNull
	@Column(name = "english_level")
	private int englishLevel;

	/** The logic question. */
	@NotNull
	@Column(name = "logic_question")
	private int logicQuestion;

	/** The tech question. */
	@NotNull
	@Column(name = "tech_question")
	private int techQuestion;

	/** The comment. */
	@NotNull
	private String comment;

	/** The interview. */
	@OneToOne(mappedBy = "motivationFeedbackId")
	@JsonIgnore
	private Interview interview;

	/**
	 * Instantiates a new motivation feedback.
	 */
	public MotivationFeedback() {
	}

	/**
	 * Instantiates a new motivation feedback.
	 *
	 * @param createMotivationFeedbackRequest the create
	 *                                        motivation
	 *                                        feedback request
	 */
	public MotivationFeedback(CreateMotivationFeedbackTO createMotivationFeedbackRequest) {
		this.standing = createMotivationFeedbackRequest.getStanding();
		this.schoolBackground = createMotivationFeedbackRequest.getSchoolBackground();
		this.motivation = createMotivationFeedbackRequest.getMotivation();
		this.softSkills = createMotivationFeedbackRequest.getSoftSkills();
		this.englishLevel = createMotivationFeedbackRequest.getEnglishLevel();
		this.logicQuestion = createMotivationFeedbackRequest.getLogicQuestion();
		this.techQuestion = createMotivationFeedbackRequest.getTechQuestion();
		this.comment = createMotivationFeedbackRequest.getComment();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStanding() {
		return standing;
	}

	public void setStanding(int standing) {
		this.standing = standing;
	}

	public int getSchoolBackground() {
		return schoolBackground;
	}

	public void setSchoolBackground(int schoolBackground) {
		this.schoolBackground = schoolBackground;
	}

	public int getMotivation() {
		return motivation;
	}

	public void setMotivation(int motivation) {
		this.motivation = motivation;
	}

	public int getSoftSkills() {
		return softSkills;
	}

	public void setSoftSkills(int softSkills) {
		this.softSkills = softSkills;
	}

	public int getEnglishLevel() {
		return englishLevel;
	}

	public void setEnglishLevel(int englishLevel) {
		this.englishLevel = englishLevel;
	}

	public int getLogicQuestion() {
		return logicQuestion;
	}

	public void setLogicQuestion(int logicQuestion) {
		this.logicQuestion = logicQuestion;
	}

	public int getTechQuestion() {
		return techQuestion;
	}

	public void setTechQuestion(int techQuestion) {
		this.techQuestion = techQuestion;
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
