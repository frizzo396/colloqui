package com.accenture.interview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 2022-10-13 NUOVA COLONNA site - START */
import com.accenture.interview.repository.SiteRepository;
/** 2022-10-13 NUOVA COLONNA site - END */
import com.accenture.interview.rto.site.SiteRTO;

/**
 * The Class SiteService.
 */
@Service
public class SiteService {
	
	/** The site repository. */
	@Autowired 
	private SiteRepository siteRepository;
	
	/**
	 * Gets the all sites.
	 *
	 * @return list of sites
	 */
	public List<SiteRTO> findAllSites() {		
		return siteRepository.findSites();		
	}	

}
