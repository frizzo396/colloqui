package com.accenture.interview.utils.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class WebPaths.
 */
@Component
public class WebPaths {

   /** The base url. */
   @Value("${colloqui.base.url}")
   private String baseUrl;

	/** The Constant HOME. */
   private static final String HOME_URL = "/interview-ms/home";
	
	/** The Constant IN_PROGRESS. */
   private static final String IN_PROGRESS_URL = "/interview-ms/interview/in-progress";
	
   /** The static final IN_PROGRESS. */
   private static final String ASSIGNED_URL = "/interview-ms/interview/search-assigned";

   /**
    * Gets the base url.
    *
    * @return the base url
    */
   public String getBaseUrl() {
      return baseUrl;
   }

   /**
    * Gets the home url.
    *
    * @return the home url
    */
   public String getHomeUrl() {
      return baseUrl + HOME_URL;
   }

   /**
    * Gets the in progress url.
    *
    * @return the in progress url
    */
   public String getInProgressUrl() {
      return baseUrl + IN_PROGRESS_URL;
   }

   /**
    * Gets the assigned url.
    *
    * @return the assigned url
    */
   public String getAssignedUrl() {
      return baseUrl + ASSIGNED_URL;
   }



}
