package com.accenture.interview.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.rto.interview.InterviewMonthRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewService;
import com.accenture.interview.service.InterviewerService;

/**
 * The Class InterviewerFacade.
 */
@Component
public class InterviewerFacade {

	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;

	/** The interview service. */
	@Autowired
	private InterviewService interviewService;

	/**
	 * Search interviewer.
	 *
	 * @param candidateName    the candidate name
	 * @param candidateSurname the candidate surname
	 * @param mail             the mail
	 * @return the search interviewer response
	 */
	public InterviewerRTO searchInterviewer(String candidateName, String candidateSurname, String mail) {
		String enterpriseId = interviewService.findEnterpriseIdByNameSurnameAndMail(candidateName, candidateSurname, mail);
		if (!ObjectUtils.isEmpty(enterpriseId)) {
			return interviewerService.findInterviewerByEnterpriseId(enterpriseId);
		} else {
			throw new IllegalStateException("Intervistatore non presente.");
		}
	}

	/**
	 * Interviewer info.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the interviewer
	 */
	public InterviewerRTO interviewerInfo(String enterpriseId) {
		return interviewerService.findInterviewerByEnterpriseId(enterpriseId);
	}

	/**
	 * Gets the in progress interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews number
	 */
	public Integer getInProgressInterviewsNumber(String enterpriseId) {
		return interviewService.getInProgressInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the my interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews number
	 */
	public Integer getMyInterviewsNumber(String enterpriseId) {
		return interviewService.getMyInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the month in progress interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the month in progress interviews number
	 */
	public Integer getMonthInProgressInterviewsNumber(String enterpriseId) {
		return interviewService.getInProgressInterviewsMonthCount(enterpriseId);
	}

	/**
	 * Gets the month completed interviews number.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the month completed interviews number
	 */
	public Integer getMonthCompletedInterviewsNumber(String enterpriseId) {
		return interviewService.getMyInterviewsMonthCount(enterpriseId);
	}

	public List<InterviewMonthRTO> getYearCompletedInterviews(String enterpriseId) {
		return interviewService.getCompletedYearInterviews(enterpriseId);
	}
}
