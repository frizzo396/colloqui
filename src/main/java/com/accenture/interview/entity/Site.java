package com.accenture.interview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/** 2022-10-13 NUOVA COLONNA site - START */

/**
 * The Class Site.
 */
@Entity(name = "Site")
@Table(name = "site")
public class Site {

	/** The site id. */
	@Id
	@NotNull
	@Column(name = "site_id")
	private Long site_id;

	/** The site name. */
	@NotNull
	@Column(name = "site_name")
	private String site_name;

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
}

/** 2022-10-13 NUOVA COLONNA site - END */
