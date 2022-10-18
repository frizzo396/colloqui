package com.accenture.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.controller.base.BaseController;
import com.accenture.interview.facade.FeedbackFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.feedback.CreateMotivationFeedbackTO;
import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertFeedback;

/**
 * The Class FeedbackController.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends BaseController {

	/** The feedback facade. */
	@Autowired
	private FeedbackFacade feedbackFacade;

	/** The check errors insert motivation feedback. */
	@Autowired
	private CheckErrorsInsertFeedback checkErrorsInsertFeedback;

	/**
	 * Adds the motivation feedback.
	 *
	 * @param createMotivationFeedbackTO the create motivation
	 *                                   feedback TO
	 * @return the response entity
	 */
	@PostMapping("/motivational/insert")
	public ResponseEntity<Object> addMotivationFeedback(@RequestBody @ModelAttribute CreateMotivationFeedbackTO createMotivationFeedbackTO) {
		ErrorRTO errorRTO = checkErrorsInsertFeedback.validate(createMotivationFeedbackTO);
		
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(feedbackFacade.insertMotivationFeedback(createMotivationFeedbackTO, interviewId)), HttpStatus.OK);

	}

	/**
	 * Adds the tech feedback.
	 *
	 * @param createTechFeedbackTO the create tech feedback TO
	 * @return the response entity
	 */
	@PostMapping("/technical/insert")
	public ResponseEntity<BaseResponseRTO> addTechFeedback(@RequestBody @ModelAttribute CreateTechFeedbackTO createTechFeedbackTO) {
		ErrorRTO errorRTO = checkErrorsInsertFeedback.validate(createTechFeedbackTO);

		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(feedbackFacade.insertTechFeedback(createTechFeedbackTO, interviewId)), HttpStatus.OK);
	}
}
