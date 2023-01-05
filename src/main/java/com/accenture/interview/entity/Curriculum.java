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
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The Class Curriculum.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "curriculum")
public class Curriculum {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/** The availabile date. */
	@Column(name = "file_name")
	private String fileName;
	
	/** The availabile date. */
	@Column(name = "upload_date")
	private Date uploadDate;
	
	/** The content. */
	@Lob
	@Column(name = "content")
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	/** The interview id. */
	@Column(name = "interview_id")
	private Long interviewId;
	
}
