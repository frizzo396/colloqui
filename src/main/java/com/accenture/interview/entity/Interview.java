package com.accenture.interview.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.utils.enums.InterviewStatusEnum;

/**
 * The Class Interview.
 */
@Entity(name = "Interview")
@Table(name = "interview")
public class Interview {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "site", referencedColumnName = "site_id")	
	private Site site;
	
	/** The candidate name. */
	@NotNull
	@Column(name = "candidate_name")
	private String candidateName;

	/** The candidate surname. */
	@NotNull
	@Column(name = "candidate_surname")
	private String candidateSurname;

	/** The candidate birth. */
	@NotNull
	@Column(name = "candidate_birth")
	private Date candidateBirth;

	/** The mail. */
	@NotNull
	private String mail;

	/** The candidate type id. */
	@ManyToOne
	@JoinColumn(name = "candidate_type_id", referencedColumnName = "id")
	private CandidateType candidateTypeId;

	/** The qualification. */
	@NotNull
	private String qualification;

	/** The status. */
	@NotNull
	private Integer status;

	/** The scheduled date. */
	@Column(name = "scheduled_date")
	private Date scheduledDate;

	/** The due date. */
	@Column(name = "due_date")
	private Date dueDate;

	/** The interview type. */
	@NotNull
	@Column(name = "interview_type")
	private long interviewType;

	/** The motivation feedback id. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "motivation_feedback_id", referencedColumnName = "id")
	private MotivationFeedback motivationFeedbackId;

	/** The tech feedback id. */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tech_feedback_id", referencedColumnName = "id")
	private TechnicalFeedback techFeedbackId;

	/** The interviewer id. */
	@ManyToOne
	@JoinColumn(name = "interviewer_id", referencedColumnName = "id")
	private Interviewer interviewerId;
	
	/** The interviewer id. */
	@Column(name = "assigner_id")
	private long assigner;

	/** The final feedback. */
	@Column(name = "final_feedback")
	private String finalFeedback;
	
	/** The curriculumId. */
	@Column(name = "curriculum_id")
	private Long curriculumId;

	/**
	 * Instantiates a new interview.
	 */
	public Interview() {
	}

	/**
	 * Instantiates a new interview.
	 *
	 * @param createInterviewRequest the create interview
	 *                               request
	 */
	public Interview(CreateInterviewTO createInterviewRequest) {
		this.candidateName = createInterviewRequest.getCandidateName();
		this.candidateSurname = createInterviewRequest.getCandidateSurname();
		this.candidateBirth = createInterviewRequest.getCandidateBirth();
		this.mail = createInterviewRequest.getMail();
		this.qualification = createInterviewRequest.getEduQualification();
		this.status = InterviewStatusEnum.NEW.getValue();
	}

	public Integer getMonth() {
		return LocalDateTime.ofInstant(dueDate.toInstant(), ZoneId.systemDefault()).getMonthValue();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateSurname() {
		return candidateSurname;
	}

	public void setCandidateSurname(String candidateSurname) {
		this.candidateSurname = candidateSurname;
	}

	public Date getCandidateBirth() {
		return candidateBirth;
	}

	public void setCandidateBirth(Date candidateBirth) {
		this.candidateBirth = candidateBirth;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public CandidateType getCandidateTypeId() {
		return candidateTypeId;
	}

	public void setCandidateTypeId(CandidateType candidateTypeId) {
		this.candidateTypeId = candidateTypeId;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public long getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(long interviewType) {
		this.interviewType = interviewType;
	}

	public MotivationFeedback getMotivationFeedbackId() {
		return motivationFeedbackId;
	}

	public void setMotivationFeedbackId(MotivationFeedback motivationFeedbackId) {
		this.motivationFeedbackId = motivationFeedbackId;
	}

	public TechnicalFeedback getTechFeedbackId() {
		return techFeedbackId;
	}

	public void setTechFeedbackId(TechnicalFeedback techFeedbackId) {
		this.techFeedbackId = techFeedbackId;
	}

	public Interviewer getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(Interviewer interviewerId) {
		this.interviewerId = interviewerId;
	}

	public String getFinalFeedback() {
		return finalFeedback;
	}

	public void setFinalFeedback(String finalFeedback) {
		this.finalFeedback = finalFeedback;
	}

	/** 2022-10-13 NUOVA COLONNA site - START */
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	/** 2022-10-13 NUOVA COLONNA site - END */

	public long getAssigner() {
		return assigner;
	}

	public void setAssigner(long assigner) {
		this.assigner = assigner;
	}

	public Long getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Long curriculumId) {
		this.curriculumId = curriculumId;
	}


	
}
