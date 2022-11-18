package com.accenture.interview.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "curriculum")
@Table(name = "curriculum")
public class Curriculum {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** The availabile date. */
	@Column(name = "file_name")
	private String fileName;
	
	/** The availabile date. */
	@Column(name = "upload_date")
	private Date uploadDate;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	/** The interview. */
	@OneToOne(mappedBy = "curriculum")
	@JsonIgnore
	private Interview interview;
	
}
