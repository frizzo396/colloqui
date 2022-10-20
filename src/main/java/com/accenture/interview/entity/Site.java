package com.accenture.interview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/** 2022-10-13 NUOVA COLONNA site - START */

/**
 * The Class Site.
 */
@Entity(name = "Site")
@Table(name = "site")
@NoArgsConstructor
@AllArgsConstructor
public class Site {

	/** The site id. */
	@Id
	@NotNull
	@Column(name = "site_id")
	private Long id;

	/** The site name. */
	@NotNull
	@Column(name = "site_name")
	private String siteName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}