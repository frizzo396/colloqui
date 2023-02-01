package com.accenture.interview.to.interview;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class RescheduledAvailabilityTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RescheduledAvailabilityTO {

   /** The interview id. */
   @NotNull(message = "availability.error.interview.not-found")
   private Long interviewId;

   /** The new date. */
   @NotEmpty(message = "availability.error.date.not-empty")
   private String newDate;

}
