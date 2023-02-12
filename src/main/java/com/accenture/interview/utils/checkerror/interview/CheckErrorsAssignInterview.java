package com.accenture.interview.utils.checkerror.interview;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.AssignInterviewTO;

import liquibase.repackaged.org.apache.commons.lang3.ObjectUtils;

/**
 * The Class CheckErrorsAssignInterview.
 */
@Component
public class CheckErrorsAssignInterview {

   /** The message source. */
   @Autowired
   private MessageSource messageSource;

   /**
    * Validate.
    *
    * @param assignInterviewTO the assign interview TO
    * @return the error RTO
    */
   public ErrorRTO validate(AssignInterviewTO assignInterviewTO) {
      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Set<ConstraintViolation<AssignInterviewTO>> violations = factory.getValidator().validate(assignInterviewTO);

      if (!violations.isEmpty()) {
         Optional<ConstraintViolation<AssignInterviewTO>> violation = violations.stream().findFirst();
         if (violation.isPresent()) {
            String errorMsg = messageSource.getMessage(violation.get().getMessage(), null, Locale.getDefault());
            return new ErrorRTO(errorMsg);
         }
      }
      if (!ObjectUtils.isEmpty(assignInterviewTO.getInterviewDate())) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
         LocalDateTime interviewLocalDate = LocalDateTime.parse(assignInterviewTO.getInterviewDate(), formatter);
         if (!(interviewLocalDate.isAfter(LocalDateTime.now()) || interviewLocalDate.isEqual(LocalDateTime.now()))) {
            String errorMsg = messageSource.getMessage("interview.error.date.not-past", null, Locale.getDefault());
            return new ErrorRTO(errorMsg);
         }
      }
      return null;
   }

}
