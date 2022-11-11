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

import com.accenture.interview.annotation.Registered;
import com.accenture.interview.facade.AvailabilityFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.ApproveAvailabilityTO;
import com.accenture.interview.to.interview.InsertAvailabilityTO;
import com.accenture.interview.utils.checkerror.CheckErrorsApproveAvailability;
import com.accenture.interview.utils.checkerror.CheckErrorsInsertAvailability;

/**
 * The Class AvailabilityController.
 */
@RestController
@RequestMapping("/availability")
public class AvailabilityController {
	
	/** The availability facade. */
	@Autowired
	private AvailabilityFacade availabilityFacade;
	
	/** The check errors insert availability. */
	@Autowired
	private CheckErrorsInsertAvailability checkErrorsInsertAvailability;
	
	/** The check errors approve availability. */
	@Autowired
	private CheckErrorsApproveAvailability checkErrorsApproveAvailability;
	
	/**
	 * Insert availability.
	 *
	 * @param insertAvailabilityTO the insert availability TO
	 * @return the response entity
	 */
	@PostMapping("/insert")
	@Registered
	public ResponseEntity<Object> insertAvailability(@RequestBody @ModelAttribute InsertAvailabilityTO insertAvailabilityTO) {
		ErrorRTO errorRTO = checkErrorsInsertAvailability.validate(insertAvailabilityTO);
		
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.addAvailabiltyInterview(insertAvailabilityTO)), HttpStatus.OK);
	}
	
	/**
	 * Insert availability.
	 *
	 * @param approveAvailabilityTO the approve availability TO
	 * @return the response entity
	 */
	@PostMapping("/approve")
	@Registered
	public ResponseEntity<Object> approveAvailability(@RequestBody @ModelAttribute ApproveAvailabilityTO approveAvailabilityTO) {
		ErrorRTO errorRTO = checkErrorsApproveAvailability.validate(approveAvailabilityTO);
		
		if (!ObjectUtils.isEmpty(errorRTO)) {
			return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseResponseRTO(availabilityFacade.approveAvailability(approveAvailabilityTO)), HttpStatus.OK);
	}

}
