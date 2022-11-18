package com.accenture.interview.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.interview.entity.Site;
import com.accenture.interview.repository.interview.SiteRepository;
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
	
	/**
	 * Find site by id.
	 *
	 * @param id the id
	 * @return the site RTO
	 */
	public SiteRTO findSiteById(Long id) {
		Optional<Site> opt = siteRepository.findById(id);
		if(opt.isPresent()) {
			return new SiteRTO(opt.get().getId(), opt.get().getSiteName());
		}
		return null;
	}

}
