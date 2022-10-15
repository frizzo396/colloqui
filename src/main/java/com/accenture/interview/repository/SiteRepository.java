package com.accenture.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Site;
import com.accenture.interview.rto.site.SiteRTO;

/** 2022-10-13 NUOVA COLONNA site - START */

/**
 * The Interface SiteRepository.
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {	
	
	/**
	 * Find all sites.
	 *
	 * @return the list
	 */
	@Query("SELECT new com.accenture.interview.rto.site.SiteRTO(s.site_id, s.site_name) FROM Site s ORDER BY s.site_name")
	List<SiteRTO> findSites();

}
