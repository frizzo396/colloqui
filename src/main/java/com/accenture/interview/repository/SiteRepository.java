package com.accenture.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.interview.entity.Site;

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
	@Query("SELECT s FROM Site s ORDER BY s.site_name")
	List<Site> findSites();

}

/** 2022-10-13 NUOVA COLONNA site - END */