package com.accenture.interview.controller.curriculum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.CurriculumFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.DownloadFileRTO;
import com.accenture.interview.rto.general.ErrorRTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.utils.checkerror.curriculum.CheckErrorsUploadCurriculum;


/**
 * The Class CurriculumController.
 */
@RestController
@RequestMapping("/curriculum")
public class CurriculumController {

   /** The cv facade. */
   @Autowired
   private CurriculumFacade cvFacade;

   @Autowired
   private CheckErrorsUploadCurriculum checkErrorsUploadCv;

   /**
    * Upload CV.
    *
    * @param uploadCvTO the upload cv TO
    * @return the response entity
    */
   @PostMapping(path = "/upload")
   public ResponseEntity<Object> uploadCV(@RequestBody UploadCvTO uploadCvTO) {
      ErrorRTO errorRTO = checkErrorsUploadCv.validate(uploadCvTO);
      if (!ObjectUtils.isEmpty(errorRTO)) {
         return new ResponseEntity<>(new BaseResponseRTO(null, errorRTO.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
      }
      BaseResponseRTO response = cvFacade.uploadCurriculum(uploadCvTO);
      if (ObjectUtils.isEmpty(response.getError())) {
         return new ResponseEntity<>(response, HttpStatus.OK);
      }
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   /**
    * Download CV.
    *
    * @param interviewId the interview id
    * @return the response entity
    */
   @GetMapping(path = "/download/{cvId}")
   public @ResponseBody ResponseEntity<Object> downloadCV(@PathVariable("cvId") Long interviewId) {
      BaseResponseRTO response = cvFacade.downloadCurriculum(interviewId);
      if(ObjectUtils.isEmpty(response.getError())) {			
         DownloadFileRTO file = (DownloadFileRTO) response.getBody();
         return ResponseEntity.ok()
               .contentType(MediaType.APPLICATION_PDF)
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
               .body(new ByteArrayResource(file.getContent()));
      }
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   }




}
