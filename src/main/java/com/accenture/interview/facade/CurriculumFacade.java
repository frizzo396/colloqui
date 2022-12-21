package com.accenture.interview.facade;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.accenture.interview.exception.CvNotFoundException;
import com.accenture.interview.exception.InterviewNotFoundException;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.DownloadFileRTO;
import com.accenture.interview.service.general.CurriculumService;
import com.accenture.interview.to.interview.UploadCvTO;

/**
 * The Class CurriculumFacade.
 */
@Component
public class CurriculumFacade {
	
	/** The cv service. */
	@Autowired
	private CurriculumService cvService;
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * Upload curriculum.
	 *
	 * @param uploadCvTO the upload cv TO
	 * @return the base response RTO
	 */
	public BaseResponseRTO uploadCurriculum(UploadCvTO uploadCvTO) {
		BaseResponseRTO response = new BaseResponseRTO();
		try {
			Long cvId = cvService.uploadCv(uploadCvTO.getCurriculum(), uploadCvTO.getInterviewId());
			response.setBody(cvId);
		} catch (InterviewNotFoundException e) {
			response.setError(messageSource.getMessage("interview.error.not-found", null, Locale.getDefault()));
		} catch (Exception e) {
			response.setError(messageSource.getMessage("curriculum.upload.error", null, Locale.getDefault()));
		}
		return response;
	}
	
	/**
	 * Download curriculum.
	 *
	 * @param interviewId the curriculum id
	 * @return the base response RTO
	 */
	public BaseResponseRTO downloadCurriculum(Long interviewId) {
		BaseResponseRTO response = new BaseResponseRTO();
		try {
			DownloadFileRTO downloadCv = cvService.downloadCv(interviewId);
			response.setBody(downloadCv);
		} catch (CvNotFoundException e) {
			response.setError(messageSource.getMessage("curriculum.download.error.not-found", null, Locale.getDefault()));
		} catch (Exception e) {
			response.setError(messageSource.getMessage("curriculum.download.error.general", null, Locale.getDefault()));
		}		
		return response;
	}

}
