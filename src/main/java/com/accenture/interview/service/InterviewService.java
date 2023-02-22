package com.accenture.interview.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.accenture.interview.entity.Availability;
import com.accenture.interview.entity.CandidateType;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.entity.Interviewer;
import com.accenture.interview.entity.MotivationFeedback;
import com.accenture.interview.entity.Site;
import com.accenture.interview.entity.TechnicalFeedback;
import com.accenture.interview.repository.interview.AvailabilityRepository;
import com.accenture.interview.repository.interview.InterviewRepository;
import com.accenture.interview.repository.interviewer.InterviewerRepository;
import com.accenture.interview.rto.candidate.CandidateTypeRTO;
import com.accenture.interview.rto.general.StartEndDateRTO;
import com.accenture.interview.rto.interview.DateListRTO;
import com.accenture.interview.rto.interview.InProgressInterviewRTO;
import com.accenture.interview.rto.interview.InterviewAndFeedbackRTO;
import com.accenture.interview.rto.interview.InterviewMonthRTO;
import com.accenture.interview.rto.interview.InterviewRTO;
import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.rto.site.SiteRTO;
import com.accenture.interview.to.interview.AssignInterviewTO;
import com.accenture.interview.to.interview.CreateInterviewTO;
import com.accenture.interview.to.interview.SearchInterviewResponsibleTO;
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

	/** The availability repository. */
	@Autowired
	private AvailabilityRepository availabilityRepository;

   /** The interviewer repository. */
   @Autowired
   private InterviewerRepository interviewerRepository;

	/**
	 * Find interview by id.
	 *
	 * @param idColloquio the id colloquio
	 * @return the interview
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
	 * @param feedback the feedback
	 * @param finalFeedback the final feedback
	 */
	public void updateInterviewTechFeedback(Long idColloquio, TechnicalFeedback feedback, String finalFeedback) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(idColloquio);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setFinalFeedback(finalFeedback);
			interview.setTechFeedbackId(feedback);
			interview.setDueDate(new Date());
         interview.setUpdatedDate(new Date());
			interview.setStatus(InterviewStatusEnum.COMPLETED.getValue());
			interviewRepository.save(interview);
		}
	}

	/**
	 * Update interview mot feedback.
	 *
	 * @param idColloquio the id colloquio
	 * @param feedback the feedback
	 * @param finalFeedback the final feedback
	 */
	public void updateInterviewMotFeedback(Long idColloquio, MotivationFeedback feedback, String finalFeedback) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(idColloquio);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setFinalFeedback(finalFeedback);
			interview.setMotivationFeedbackId(feedback);
			interview.setDueDate(new Date());
         interview.setUpdatedDate(new Date());
			interview.setStatus(InterviewStatusEnum.COMPLETED.getValue());
			interviewRepository.save(interview);
		}
	}

	/**
	 * Find interview by name surname and mail.
	 *
	 * @param name the name
	 * @param surname the surname
	 * @param email the email
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
	 * Adds the new interview.
	 *
	 * @param request the request
	 * @param type the type
	 * @param interviewer the interviewer
	 * @param site the site
	 * @param assigner the assigner
	 * @return the long
	 */
	public Long addNewInterview(CreateInterviewTO request, CandidateTypeRTO type, InterviewerRTO interviewer, SiteRTO site, InterviewerRTO assigner) {
		Interview interview = new Interview(request);
		interview.setSite(new Site(site.getId(), site.getName()));
		interview.setCandidateTypeId(new CandidateType(type.getId(), type.getDescription()));
		interview.setAssigner(assigner.getId());		
      interview.setInterviewerId(interviewer != null ? new Interviewer(interviewer.getId(), interviewer.getEnterpriseId(), interviewer.getMail(), interviewer.getType()) : null);
      interview.setInterviewType(request.getInterviewType());
		interview.setStatus(InterviewStatusEnum.NEW.getValue());
		Interview saved = interviewRepository.save(interview);
		return saved.getId();
	}

	/**
	 * Search interview.
	 *
	 * @param searchInterviewTO the search interview TO
	 * @return the list
	 */
   public List<InterviewAndFeedbackRTO> searchInterviews(SearchInterviewTO searchInterviewTO) {
		return interviewRepository.findInterviewByParams(searchInterviewTO.getCandidateName(), 
				searchInterviewTO.getCandidateSurname(),
            searchInterviewTO.getInterviewType(), searchInterviewTO.getFirstDate(),
				searchInterviewTO.getSecondDate(), searchInterviewTO.getEnterpriseId(),
				searchInterviewTO.getCandidateType(), searchInterviewTO.getSite());
	}

   /**
    * Search assigned interviews.
    *
    * @param searchInterviewTO the search interview TO
    * @return the list
    */
   public List<InterviewAndFeedbackRTO> searchInterviews(SearchInterviewResponsibleTO searchInterviewTO) {
      return interviewRepository.findInterviewByParams(searchInterviewTO.getCandidateName(),
            searchInterviewTO.getInterviewType(),
            searchInterviewTO.getEnterpriseId(),
            searchInterviewTO.getCandidateType(),
            searchInterviewTO.getSite(),
            searchInterviewTO.getStatus(),
            searchInterviewTO.getFeedback());
   }

	/**
	 * Gets the completed interviews.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the completed interviews
	 */
	public List<InterviewAndFeedbackRTO> getCompletedInterviews(String enterpriseId) {
		return this.interviewRepository.findCompletedInterviewsByEnterpriseId(enterpriseId);

	}

	/**
	 * Gets the assigned interviews.
	 *
	 * @return the assigned interviews
	 */
   public List<InterviewAndFeedbackRTO> getAssignedInterviews() {
      List<InterviewAndFeedbackRTO> interviews = this.interviewRepository.findAssignedInterviews();

		for(InterviewAndFeedbackRTO interview: interviews) {
			List<Date> availabilities = availabilityRepository.findAvailabilityDatesByInterviewId(interview.getIdColloquio());
			interview.setAvailabilityDates(new DateListRTO(availabilities));
		}		
		return interviews;

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
	 * Gets the my interviews month count.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the my interviews month count
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
	 * Find interview with mail params.
	 *
	 * @param interviewId the interview id
	 * @return the interview RTO
	 */
	public InterviewRTO findInterviewWithMailParams(Long interviewId) {
		return interviewRepository.findInterviewWithMailParams(interviewId);
	}

   /**
    * Find interview to reassign.
    *
    * @param interviewId the interview id
    * @return the interview RTO
    */
   public InterviewRTO findInterviewToReassign(Long interviewId) {
      return interviewRepository.findInterviewToReassign(interviewId);
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



	/**
	 * Refuse interview.
	 *
	 * @param interviewId the interview id
	 * @return the long
	 */
	public Long refuseInterview(Long interviewId) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
         interview.setUpdatedDate(new Date());
			interview.setStatus(InterviewStatusEnum.REFUSED.getValue());
			interviewRepository.save(interview);	
			return interviewId;
		}
		return null;
	}
	
	/**
	 * Reassign interview.
	 *
	 * @param interviewId the interview id
	 * @param newInterviewer the new interviewer
	 * @return the long
	 */
	public Long reassignInterview(Long interviewId, InterviewerRTO newInterviewer) {
		Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			interview.setInterviewerId(new Interviewer(newInterviewer.getId(), 
					newInterviewer.getEnterpriseId(), 
					newInterviewer.getMail(), 
					newInterviewer.getType()));
         interview.setUpdatedDate(new Date());
			interview.setStatus(InterviewStatusEnum.NEW.getValue());
			interviewRepository.save(interview);	
			return interviewId;
		}
		return null;
	}
	
   /**
    * Accept rescheduled.
    *
    * @param interviewId the interview id
    * @return the long
    */
   public Long acceptRescheduled(Long interviewId) {
      Optional<Interview> optInterview = interviewRepository.findInterviewById(interviewId);
      if (optInterview.isPresent()) {
         Interview interview = optInterview.get();
         interview.setStatus(InterviewStatusEnum.SCHEDULED.getValue());
         interview.setUpdatedDate(new Date());
         interviewRepository.save(interview);
         return interviewId;
      }
      return null;
   }
	
	/**
	 * Find last interview id.
	 *
	 * @param enterpriseId the enterprise id
	 * @return the long
	 */
	public Long findLastInterviewId(String enterpriseId) {
		Interview interview = interviewRepository.findLastInterviewByEnterpriseId(enterpriseId);
		return interview.getId();
	}
	
	/**
    * Gets the completed interviews.
    *
    * @return the completed interviews
    */
	public List<InterviewAndFeedbackRTO> getCompletedInterviews() {
		return this.interviewRepository.findCompletedInterviews();
	}	

   /**
    * Assign interview.
    *
    * @param assignInterviewTO the assign interview TO
    * @return the interview RTO
    */
   public InterviewRTO assignInterview(AssignInterviewTO assignInterviewTO) {
      Optional<Interviewer> optInterviewer = interviewerRepository.findInterviewerEntityByEnterpriseId(assignInterviewTO.getEnterpriseId());
      Optional<Interview> optInterview = interviewRepository.findById(assignInterviewTO.getInterviewId());
      if (optInterviewer.isPresent() && optInterview.isPresent()) {
         Interview interview = optInterview.get();
         interview.setInterviewerId(optInterviewer.get());

         if (!ObjectUtils.isEmpty(assignInterviewTO.getInterviewDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime interviewLocalDate = LocalDateTime.parse(assignInterviewTO.getInterviewDate(), formatter);
            Date interviewDate = Date.from(interviewLocalDate.atZone(ZoneId.systemDefault()).toInstant());
            interview.setStatus(InterviewStatusEnum.SCHEDULED.getValue());
            interview.setUpdatedDate(new Date());
            interview.setScheduledDate(interviewDate);
         }
         interviewRepository.save(interview);
         Optional<Interviewer> assigner = interviewerRepository.findById(interview.getAssigner());
         return new InterviewRTO(interview.getId(), interview.getSite().getSiteName(), interview.getCandidateName(), interview.getCandidateSurname(), interview.getMail(),
               optInterviewer.get().getMail(), assigner.get().getMail(), interview.getNote());
      }
      return null;
   }

   /**
    * Unassign interview.
    *
    * @param interviewId the interview id
    */
   public void unassignInterview(Long interviewId) {
      Optional<Interview> optInterview = interviewRepository.findById(interviewId);
      List<Availability> availabilityList = availabilityRepository.findAvailabilityByInterviewId(interviewId);
      if (optInterview.isPresent()) {
         Interview interview = optInterview.get();
         interview.setInterviewerId(null);
         interview.setStatus(InterviewStatusEnum.NEW.getValue());
         interview.setUpdatedDate(new Date());
         interviewRepository.save(interview);
      }
      availabilityRepository.deleteAll(availabilityList);
   }

}
