package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.interview.annotation.Registered;
import com.accenture.interview.facade.CurriculumFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertInterview;
import com.accenture.interview.utils.checkerror.CheckErrorsUploadCurriculum;

/**
 * The Class InterviewController.
 */
@Controller
@RequestMapping("/interview")
public class InterviewController {

	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;
	
	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;
	
	/** The cv facade. */
	@Autowired
	private CurriculumFacade cvFacade;
	
	/** The check errors insert interview. */
	@Autowired
	private CheckErrorsInsertInterview checkErrorsInsertInterview;
	
	/** The check errors upload cv. */
	@Autowired
	private CheckErrorsUploadCurriculum checkErrorsUploadCv;
	


	/**
	 * Creates the interview.
	 *
	 * @param createInterviewTO the create interview TO
	 * @return the response entity
	 */
	@PostMapping(path = "/create")
	@Registered	
	public @ResponseBody ResponseEntity<Object> createInterview(@RequestBody @ModelAttribute CreateInterviewTO createInterviewTO) {
		ErrorRTO errorRTO = checkErrorsInsertInterview.validate(createInterviewTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(interviewFacade.addNewInterview(createInterviewTO)), HttpStatus.OK);
	}
	
	/**
	 * Upload CV.
	 *
	 * @param uploadCvTO the upload cv TO
	 * @return the response entity
	 */
	@PostMapping(path = "/upload-cv")
	@Registered	
	public @ResponseBody ResponseEntity<Object> uploadCV(@ModelAttribute UploadCvTO uploadCvTO) {
		ErrorRTO errorRTO = checkErrorsUploadCv.validate(uploadCvTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(cvFacade.uploadCurriculum(uploadCvTO), HttpStatus.OK);
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/search")
	@Registered	
	public String searchInterview(@RequestBody @ModelAttribute SearchInterviewTO searchInterviewTO, Model model) {
		model.addAttribute("interviewer", interviewerFacade.interviewerInfo(System.getProperty("user.name")));
		model.addAttribute("searchInterviews", interviewFacade.searchInterviews(searchInterviewTO));
		model.addAttribute("searchInterviewTO", new SearchInterviewTO());
		model.addAttribute("comboSitesDB", interviewFacade.getComboSites());	
		model.addAttribute("registerUserTO", new RegisterInterviewerTO());
		return "search";
	}

}
