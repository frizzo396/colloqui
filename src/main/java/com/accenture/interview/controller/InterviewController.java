package com.accenture.interview.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertInterview;
import com.accenture.interview.utils.checkerror.CheckErrorsSearchInterview;

/**
 * The Class InterviewController.
 */
@Controller
@RequestMapping("/interview")
public class InterviewController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;
	
	@Autowired
	private InterviewerFacade interviewerFacade;

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
	@ResponseBody
	public ResponseEntity<Object> createInterview(@RequestBody @ModelAttribute CreateInterviewTO createInterviewTO) {
		ErrorRTO errorRTO = checkErrorsInsertInterview.validate(createInterviewTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
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
	public String searchInterview(@RequestBody @ModelAttribute SearchInterviewTO searchInterviewTO, Model model) {
		model.addAttribute("interviewer", interviewerFacade.interviewerInfo(System.getProperty("user.name")));
		model.addAttribute("searchInterviews", interviewFacade.searchInterviews(searchInterviewTO));
		model.addAttribute("searchInterviewTO", new SearchInterviewTO());
		model.addAttribute("comboSitesDB", interviewFacade.getComboSites());	
		return "search";
	}

}
