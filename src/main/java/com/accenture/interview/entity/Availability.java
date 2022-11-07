package com.accenture.interview.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;

/**
 * The Class Availability.
 */
@Entity(name = "Availability")
@Table(name = "availability")
@NoArgsConstructor
public class Availability {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** The interview. */
	@ManyToOne
	@JoinColumn(name = "interview_id", referencedColumnName = "id")
	private Interview interview;
	

	/** The availabile date. */
	@NotNull
	@Column(name = "int_date")
	private Date availabileDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

	public Date getAvailabileDate() {
		return availabileDate;
	}

	public void setAvailabileDate(Date availabileDate) {
		this.availabileDate = availabileDate;
	}
	
	public Availability(Interview interview, Date availabileDate) {
		this.interview = interview;
		this.availabileDate = availabileDate;
	}
	
}
