package com.accenture.interview.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;

/**
 * The Class CandidateType.
 */
@Entity(name = "CandidateType")
@Table(name = "candidate_type")
@NoArgsConstructor
public class CandidateType {

	/** The id. */
	@Id
	@NotNull
	private long id;

	/** The description. */
	@NotNull
	private String description;

	/** The interview. */
	@OneToMany(mappedBy = "candidateTypeId")
	@JsonIgnore
	private List<Interview> interview;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Interview> getInterview() {
		return interview;
	}

	public void setInterview(List<Interview> interview) {
		this.interview = interview;
	}

	public CandidateType(@NotNull long id, @NotNull String description) {
		super();
		this.id = id;
		this.description = description;
	}
	

}
