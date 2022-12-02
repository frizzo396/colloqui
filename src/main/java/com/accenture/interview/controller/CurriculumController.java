package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.annotation.Registered;
import com.accenture.interview.facade.CurriculumFacade;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.DownloadFileRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;

/**
 * The Class AvailabilityController.
 */
@RestController
@RequestMapping("/interview/curriculum")
public class CurriculumController {
	
	/** The interview facade. */
	@Autowired
	private InterviewFacade interviewFacade;

	/** The interviewer facade. */
	@Autowired
	private InterviewerFacade interviewerFacade;

	/** The cv facade. */
	@Autowired
	private CurriculumFacade cvFacade;
	
	/**
	 * Upload CV.
	 *
	 * @param uploadCvTO the upload cv TO
	 * @return the response entity
	 */
	@PostMapping(path = "/upload")
	@Registered	
	public ModelAndView uploadCV(@ModelAttribute UploadCvTO uploadCvTO) {
		String username = System.getProperty("user.name");
		ModelAndView modelAndView = new ModelAndView();
		BaseResponseRTO response = cvFacade.uploadCurriculum(uploadCvTO);
		if(ObjectUtils.isEmpty(response.getError())) {
			modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
			modelAndView.addObject("inProgressInterviews", interviewFacade.getInProgressInterviewsNumber(username));
			modelAndView.addObject("myInterviews", interviewFacade.getMyInterviewsNumber(username));
			modelAndView.addObject("myInterviewsMonth", interviewFacade.getMonthCompletedInterviewsNumber(username));
			modelAndView.addObject("inProgressInterviewsMonth", interviewFacade.getMonthInProgressInterviewsNumber(username));
			modelAndView.addObject("yearMonthInterviews", interviewFacade.getYearCompletedInterviews(username));
			modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
			modelAndView.setViewName("home.html");
			return modelAndView;
		} 
		else {
			modelAndView.addObject("interviewer", interviewerFacade.interviewerInfo(username));
			modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
			modelAndView.addObject("interviewerList", interviewerFacade.findAllInterviewers());
			modelAndView.addObject("comboSitesDB", interviewFacade.getComboSites());
			modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
			modelAndView.addObject("uploadCvTO", new UploadCvTO());
			modelAndView.setViewName("insert.html");
			return modelAndView;
		}
	}

	/**
	 * Download CV.
	 *
	 * @param interviewId the interview id
	 * @return the response entity
	 */
	@GetMapping(path = "/download/{cvId}")
	@Registered	
	public @ResponseBody ResponseEntity<Object> downloadCV(@PathVariable("cvId")Long interviewId) {
		BaseResponseRTO response = cvFacade.downloadCurriculum(interviewId);
		if(ObjectUtils.isEmpty(response.getError())) {			
			DownloadFileRTO file = (DownloadFileRTO) response.getBody();
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_PDF)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
					.body(new ByteArrayResource(file.getContent()));
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
