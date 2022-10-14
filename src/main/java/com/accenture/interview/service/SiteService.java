package com.accenture.interview.service;

import java.util.ArrayList;
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
		
		List<Site> result;
		
		try {
			result = siteRepository.findSites();
		}
		catch(Exception ex) {
			// per test
			result = new ArrayList<Site>();  
			
			Site site_1 = new Site();
			Long val_1 = (long) 1;
			site_1.setSite_id(val_1);
			site_1.setSite_name("Bologna"); 
			result.add(site_1);
			  
			Site site_2 = new Site();
			Long val_2 = (long) 2;
			site_2.setSite_id(val_2);			
			site_2.setSite_name("Venezia"); 
			result.add(site_2);
			
			System.out.println("***** ERRORE findSitesFromDB() ***** " + ex.getMessage());
		}
		return result;		
	}	
	/** 2022-10-13 NUOVA COLONNA site - END */	

}
