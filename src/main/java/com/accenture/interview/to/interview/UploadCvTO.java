package com.accenture.interview.to.interview;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class ApproveAvailabilityTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadCvTO {
	
	/** The interview id. */
	@NotNull(message = "interview.error.not-found")
	private Long interviewId;
	
	/** The curriculum. */
	@NotNull(message = "curriculum.not-empty")
	private MultipartFile curriculum;

}
