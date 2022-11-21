package com.accenture.interview.rto.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class DownloadFileRTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileRTO {
	
	/** The file name. */
	private String fileName;
	
	/** The content. */
	private byte[] content;

}
