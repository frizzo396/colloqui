package com.accenture.interview.to.interview;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class AssignInterviewTO.
 */
@Data
@NoArgsConstructor
public class AssignInterviewTO {

   /** The interview id. */
   @NotNull(message = "availability.error.refuse.interview.empty")
   private Long interviewId;

   /** The enterprise id. */
   @NotEmpty(message = "interview.error.enterpriseid.not-empty")
   private String enterpriseId;

   /** The interview date. */
   private String interviewDate;

}
