package com.accenture.interview.utils.checkerror;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.to.interview.SearchInterviewTO;

/**
 * The Class CheckErrorsSearchInterview.
 */
@Component
public class CheckErrorsSearchInterview {

	/**
	 * Validate.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the sets the
	 */
	public Set<String> validate(SearchInterviewTO searchInterviewTO) {
		Set<String> errorsSet = new HashSet<>();
		if (this.searchInterviewsEmptyFields(searchInterviewTO)) {
			errorsSet.add("Almeno un campo deve essere valorizzato.");
		}
		return errorsSet;
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
				ObjectUtils.isEmpty(searchInterviewTO.getEmail()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getInterviewType()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getFirstDate()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getSecondDate()) &&
				ObjectUtils.isEmpty(searchInterviewTO.getEnterpriseId()));
	}

}
