package com.accenture.interview.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.entity.CandidateType;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.Site;
import com.accenture.interview.entity.TechFeedback;
import com.accenture.interview.repository.InterviewRepository;
import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.general.StartEndDateRTO;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewMonthRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewTO;
import com.accenture.interview.utils.date.DateUtils;
import com.accenture.interview.utils.enums.InterviewStatusEnum;
import com.accenture.interview.utils.enums.InterviewTypeEnum;

/**
 * The Class InterviewService.
 */
@Service
public class InterviewService {

	/** The interview repository. */
	@Autowired
	private InterviewRepository interviewRepository;

	/**
	 * Gets the interview from id.
	 *
	 * @param idColloquio the id colloquio
	 * @return the interview from id
	 */
	public Interview findInterviewById(long idColloquio) {
		Optional<Interview> opt = interviewRepository.findInterviewById(idColloquio);

		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	/**
	 * Update interview tech feedback.
	 *
	 * @param idColloquio the id colloquio
	 * @param feedback      the feedback
	 * @param finalFeedback the final feedback
	 */
	public void updateInterviewTechFeedback(int idColloquio, TechFeedback feedback, String finalFeedback) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(idColloquio);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setFinalFeedback(finalFeedback);
			interview.setTechFeedbackId(feedback);
			interview.setDueDate(new Date());
			interview.setStatus(InterviewStatusEnum.COMPLETED.getValue());
			interviewRepository.save(interview);
		}
	}

	/**
	 * Update interview mot feedback.
	 *
	 * @param idColloquio the id colloquio
	 * @param feedback      the feedback
	 * @param finalFeedback the final feedback
	 */
	public void updateInterviewMotFeedback(int idColloquio, MotivationFeedback feedback, String finalFeedback) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(idColloquio);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setFinalFeedback(finalFeedback);
			interview.setMotivationFeedbackId(feedback);
			interview.setDueDate(new Date());
			interview.setStatus(InterviewStatusEnum.COMPLETED.getValue());
			interviewRepository.save(interview);
		}
	}

	/**
	 * Find interview by name surname and mail.
	 *
	 * @param name    the name
	 * @param surname the surname
	 * @param email   the email
	 * @return the interview
	 */
	public Interview findInterviewByNameSurnameAndMail(String name, String surname, String email) {
		Optional<Interview> opt = interviewRepository.findInterviewByNameSurnameAndMail(name, surname, email);

		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/**
	 * Find interview by mail.
	 *
	 * @param email the email
	 * @return the interview
	 */
	public Interview findInterviewByMail(String email) {
		Optional<Interview> opt = interviewRepository.findInterviewByMail(email);

		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	/**
	 * Find interviewer by name surname and mail.
	 *
	 * @param name the name
	 * @param surname the surname
	 * @param email the email
	 * @return the string
	 */
	public String findEnterpriseIdByNameSurnameAndMail(String name, String surname, String email) {
		Optional<Interview> opt = interviewRepository.findInterviewByNameSurnameAndMail(name, surname, email);
		
		if(opt.isPresent()) {
			return opt.get().getInterviewerId().getEnterpriseId();
		}
		return null;
	}

	/**
	 * Adds the new interview.
	 *
	 * @param request       the request
	 * @param type the type
	 * @param interviewer   the interviewer
	 * @param site the site
	 * @param assigner the assigner
	 * @return the creates the interview response
	 */
	public Interview addNewInterview(CreateInterviewTO request, CandidateTypeRTO type, InterviewerRTO interviewer, SiteRTO site, InterviewerRTO assigner) {
		Interview interview = new Interview(request);
		interview.setSite(new Site(site.getId(), site.getName()));
		interview.setCandidateTypeId(new CandidateType(type.getId(), type.getDescription()));
		interview.setAssigner(assigner.getId());		
		interview.setInterviewerId(new Interviewer(interviewer.getId(), interviewer.getEnterpriseId(), interviewer.getMail(), interviewer.getType()));				
		interview.setInterviewType(getInterviewTypeFromString(request.getInterviewType()));
		interview.setStatus(InterviewStatusEnum.IN_PROGRESS.getValue());
		return interviewRepository.save(interview);
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview
	 * 
	 * @return the list
	 */
	public List<InterviewAndFeedbackRTO> searchInterview(SearchInterviewTO searchInterviewTO) {
		Long interviewType = getInterviewTypeFromString(searchInterviewTO.getInterviewType());
		return interviewRepository.findInterviewByParams(searchInterviewTO.getCandidateName(), 
				searchInterviewTO.getCandidateSurname(),
				interviewType, searchInterviewTO.getFirstDate(),
				searchInterviewTO.getSecondDate(), searchInterviewTO.getEnterpriseId(),
				searchInterviewTO.getCandidateType(), searchInterviewTO.getSite());
	}

	/**
	 * Gets the my interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews
	 */
	public List<InterviewAndFeedbackRTO> getCompletedInterviews(String enterpriseId) {
		return this.interviewRepository.findCompletedInterviewsByEnterpriseId(enterpriseId);

	}
	
	/**
	 * Gets the assigned interviews.
	 *
	 * @param assignerId the assigner id
	 * @return the assigned interviews
	 */
	public List<InterviewAndFeedbackRTO> getAssignedInterviews(long assignerId) {
		return this.interviewRepository.findAssignedInterviews(assignerId);

	}

	/**
	 * Gets the in progress interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews
	 */
	public List<InProgressInterviewRTO> getInProgressInterviews(String enterpriseId) {
		return interviewRepository.getInProgressInterviewsByEnterpriseId(enterpriseId);
	}

	/**
	 * Gets the in progress interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews count
	 */
	public int getInProgressInterviewsCount(String enterpriseId) {
		return interviewRepository.getInProgressInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the my interviews count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews count
	 */
	public int getMyInterviewsCount(String enterpriseId) {
		return interviewRepository.getCompletedInterviewsCount(enterpriseId);
	}

	/**
	 * Gets the in progress interviews month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews month count
	 */
	public Integer getMyInterviewsMonthCount(String enterpriseId) {
		StartEndDateRTO dates = DateUtils.calculateMonthDateIntervals();
		return interviewRepository.findCompletedInterviewsMonthCount(enterpriseId, dates.getStartDate(), dates.getEndDate());
	}

	/**
	 * Gets the in progress interviews month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the in progress interviews month count
	 */
	public Integer getInProgressInterviewsMonthCount(String enterpriseId) {
		StartEndDateRTO dates = DateUtils.calculateMonthDateIntervals();
		return interviewRepository.getInProgressMonthCount(enterpriseId, dates.getStartDate(), dates.getEndDate());
	}

	/**
	 * Gets the completed year interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the completed year interviews
	 */
	public List<InterviewMonthRTO> getCompletedYearInterviews(String enterpriseId) {
		StartEndDateRTO dates = DateUtils.calculateYearDateIntervals();
		List<Interview> interviews = interviewRepository.findYearInterviews(enterpriseId, dates.getStartDate(), dates.getEndDate());
		List<InterviewMonthRTO> interviewMonth = DateUtils.createEmptyInterviewMonthList();
		Map<Integer, List<Interview>> interviewsPerMonth = interviews
				.stream()
				.collect(Collectors.groupingBy(Interview::getMonth));

		for (Entry<Integer, List<Interview>> entry : interviewsPerMonth.entrySet()) {
			Optional<InterviewMonthRTO> intRto = interviewMonth.stream().filter(a -> a.getMonth().equals(entry.getKey())).findFirst();
			if (intRto.isPresent()) {
				intRto.get().setInterviews(entry.getValue().size());
			}
		}

		return interviewMonth;
	}

	/**
	 * Gets the interview type from string.
	 *
	 * @param description the description
	 * @return the interview type from string
	 */
	public Long getInterviewTypeFromString(String description) {

		if (!ObjectUtils.isEmpty(description)) {

			try {
				Optional<Long> optType = Optional
						.of(InterviewTypeEnum.valueOf(description))
						.map(InterviewTypeEnum::getId);

				if (optType.isPresent()) {
					return optType.get();
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
