package com.accenture.interview.utils.checkerror.interview;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.SearchInterviewTO;

/**
 * The Class CheckErrorsSearchInterview.
 */
@Component
public class CheckErrorsSearchInterview {
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Validate.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the error RTO
	 */
	public ErrorRTO validate(SearchInterviewTO searchInterviewTO) {
		if (this.searchInterviewsEmptyFields(searchInterviewTO)) {
			return new ErrorRTO(messageSource.getMessage("interview.error.search.almost-one-field", null, Locale.getDefault()));
		}
		return null;
	}

	/**
	 * Search interviews empty fields.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return true, if successful
	 */
	private boolean searchInterviewsEmptyFields(SearchInterviewTO searchInterviewTO) {
		return (ObjectUtils.isEmpty(searchInterviewTO.getCandidateName()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getCandidateSurname()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getInterviewType()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getFirstDate()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getSecondDate()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getEnterpriseId()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getCandidateType()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getSite()));
	}

}
