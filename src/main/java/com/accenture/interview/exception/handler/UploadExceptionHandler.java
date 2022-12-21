package com.accenture.interview.exception.handler;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.entity.Interview;
import com.accenture.interview.facade.InterviewFacade;
import com.accenture.interview.facade.InterviewerFacade;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.service.SiteService;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.to.interviewer.RegisterInterviewerTO;

/**
 * The Class UploadExceptionHandler.
 */
@ControllerAdvice
public class UploadExceptionHandler {
	
	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;
	
	/** The interview service. */
	@Autowired
	private InterviewService interviewService;
	
	/** The site service. */
	@Autowired
	private SiteService siteService;
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Handle max upload size exceeded exception.
	 *
	 * @param ex the ex
	 * @return the model and view
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxUploadSizeExceededException(Exception ex){
		return redirectInsert();
	}
	
	/**
	 * Handle IO exception.
	 *
	 * @param ex the ex
	 * @return the model and view
	 */
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(Exception ex){
		return redirectInsert();
	}
	
	
	/**
	 * Redirect insert.
	 *
	 * @return the model and view
	 */
	private ModelAndView redirectInsert() {
		ModelAndView modelAndView = new ModelAndView();
		String enterpriseId = System.getProperty("user.name");
		modelAndView.addObject("interviewer", interviewerService.findInterviewerByEnterpriseId(enterpriseId));
		modelAndView.addObject("createInterviewTO", new CreateInterviewTO());
		modelAndView.addObject("interviewerList", interviewerService.findAllInterviewers());
		modelAndView.addObject("comboSitesDB", siteService.findAllSites());
		modelAndView.addObject("registerUserTO", new RegisterInterviewerTO());
		modelAndView.addObject("uploadCvTO", new UploadCvTO());
		modelAndView.addObject("uploadError", messageSource.getMessage("curriculum.upload.error.max-size", null, Locale.getDefault()));
		modelAndView.addObject("createdInterview", interviewService.findLastInterviewId(enterpriseId));
		modelAndView.setViewName("insert.html");
		return modelAndView;
	}

}
