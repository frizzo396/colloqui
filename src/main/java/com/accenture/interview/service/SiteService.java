package com.accenture.interview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Site;
/** 2022-10-13 NUOVA COLONNA site - START */
import com.accenture.interview.repository.SiteRepository;
/** 2022-10-13 NUOVA COLONNA site - END */

/**
 * The Class SiteService.
 */
@Service
public class SiteService {
	
	/** 2022-10-13 NUOVA COLONNA site - START */
	/** The site repository. */
	@Autowired private SiteRepository siteRepository;
	
	/**
	 * Gets the all sites.
	 *
	 * @return list of sites
	 */
	// 2022-10-14 PROVO A TOGLIERE static AL METODO
	public List<Site> findSitesFromDB() {		

		return siteRepository.findSites();		
	}	
	/** 2022-10-13 NUOVA COLONNA site - END */	

}
