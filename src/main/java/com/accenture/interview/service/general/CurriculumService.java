package com.accenture.interview.service.general;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.interview.entity.Curriculum;
import com.accenture.interview.entity.Interview;
import com.accenture.interview.exception.InterviewNotFoundException;
import com.accenture.interview.repository.curriculum.CurriculumRepository;
import com.accenture.interview.repository.interview.InterviewRepository;

/**
 * The Class CurriculumService.
 */
@Service
public class CurriculumService {
	
	/** The cv repository. */
	@Autowired
	private CurriculumRepository cvRepository;
	
	/** The interview repository. */
	@Autowired
	private InterviewRepository interviewRepository;
		
	/**
	 * Upload cv.
	 *
	 * @param curriculum the curriculum
	 * @param interviewId the interview id
	 * @return the long
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterviewNotFoundException the interview not found exception
	 */
	public Long uploadCv(MultipartFile curriculum, Long interviewId) throws IOException, InterviewNotFoundException {			
		Optional<Interview> optInterview = interviewRepository.findById(interviewId);
		
		if(optInterview.isPresent()) {
			Interview interview = optInterview.get();
			Curriculum cv = createCurriculumEntity(curriculum, interview);			
			cvRepository.save(cv);
			interview.setCurriculum(cv);
			interviewRepository.save(interview);
			return cv.getId();
		} else {
			throw new InterviewNotFoundException();
		}				
	}
	
	
	/**
	 * Creates the curriculum entity.
	 *
	 * @param curriculum the curriculum
	 * @return the curriculum
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Curriculum createCurriculumEntity(MultipartFile curriculum, Interview interview) throws IOException {
		Curriculum cv = new Curriculum();
		cv.setContent(curriculum.getBytes());
		cv.setFileName(curriculum.getOriginalFilename());
		cv.setUploadDate(new Date());
		cv.setInterview(interview);
		return cv;
	}

}
