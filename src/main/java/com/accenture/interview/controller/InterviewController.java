package com.accenture.interview.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertInterview;
import com.accenture.interview.utils.checkerror.CheckErrorsSearchInterview;

/**
 * The Class InterviewController.
 */
@RestController
@RequestMapping("/interview")
public class InterviewController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	/** The check errors insert interview. */
	@Autowired
	private CheckErrorsInsertInterview checkErrorsInsertInterview;

	/** The check errors search interview. */
	@Autowired
	private CheckErrorsSearchInterview checkErrorsSearchInterview;

	/**
	 * Creates the interview.
	 *
	 * @param createInterviewTO the create interview TO
	 * @return the response entity
	 */
	@PostMapping("/create")
	public ResponseEntity<Object> createInterview(@RequestBody @ModelAttribute CreateInterviewTO createInterviewTO) {
		Set<String> errorMessages = checkErrorsInsertInterview.validate(createInterviewTO);
		Optional<String> error = errorMessages.stream().findFirst();

		if (error.isPresent()) {
			return new ResponseEntity<>(new BaseResponseRTO(null, error.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.addNewInterview(createInterviewTO)), HttpStatus.OK);
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview request
	 * @return the response entity
	 */
	@PostMapping("/search")
	public ResponseEntity<Object> searchInterview(@RequestBody @ModelAttribute SearchInterviewTO searchInterviewTO) {
		Set<String> errorMessages = checkErrorsSearchInterview.validate(searchInterviewTO);
		Optional<String> error = errorMessages.stream().findFirst();

		if (error.isPresent()) {
			return new ResponseEntity<>(new BaseResponseRTO(null, error.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.searchInterviews(searchInterviewTO)), HttpStatus.OK);
	}

}
