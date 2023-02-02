package com.accenture.interview.utils.checkerror.availability;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.exception.GenericException;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.to.interview.RescheduledAvailabilityTO;

/**
 * The Class CheckErrorsAcceptAvailability.
 */
@Component
public class CheckErrorsAcceptAvailability {

   /** The interview service. */
   @Autowired
   private InterviewService interviewService;

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /**
    * Validate.
    *
    * @param rescheduleTO the reschedule TO
    * @return the error RTO
    */
   public ErrorRTO validate(RescheduledAvailabilityTO rescheduleTO) {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Set<ConstraintViolation<RescheduledAvailabilityTO>> violations = factory.getValidator().validate(rescheduleTO);

      try {
         if (!violations.isEmpty()) {
            ConstraintViolation<RescheduledAvailabilityTO> violation = violations.stream().findFirst().orElseThrow(GenericException::new);
            String errorMsg = messageSource.getMessage(violation.getMessage(), null, Locale.getDefault());
            return new ErrorRTO(errorMsg);
         }
         if (ObjectUtils.isEmpty(interviewService.findInterviewById(rescheduleTO.getInterviewId()))) {
            String errorMsg = messageSource.getMessage("interview.error.not-found", null, Locale.getDefault());
            return new ErrorRTO(errorMsg);
         }
      } catch (GenericException e) {
         return new ErrorRTO("Errore generico");
      }
      return null;
   }

}
