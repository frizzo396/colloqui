package com.accenture.interview.to.interview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class SearchAssignedTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchInterviewResponsibleTO {

   /** The candidate name. */
   private String candidateName;

   /** The interview type. */
   private Long interviewType;

   /** The enterprise id. */
   private String enterpriseId;

   /** The candidate type. */
   private String candidateType;

   /** The site. */
   private Long site;

   /** The status. */
   private Integer status;

   /** The status. */
   private String feedback;

}
