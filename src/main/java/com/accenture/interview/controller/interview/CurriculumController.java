package com.accenture.interview.controller.interview;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.facade.CurriculumFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.rto.general.DownloadFileRTO;
import com.accenture.interview.to.interview.UploadCvTO;
import com.accenture.interview.utils.constants.PaginationConstants;
import com.accenture.interview.utils.page.RedirectUtils;

/**
 * The Class CurriculumController.
 */
@RestController
@RequestMapping("/interview/curriculum")
public class CurriculumController {

   /** The cv facade. */
   @Autowired
   private CurriculumFacade cvFacade;

   /** The redirect utils. */
   @Autowired
   private RedirectUtils redirectUtils;

   /**
    * Upload CV.
    *
    * @param uploadCvTO the upload cv TO
    * @param session the session
    * @return the model and view
    */
   @PostMapping(path = "/upload")
   public ModelAndView uploadCV(@ModelAttribute UploadCvTO uploadCvTO, HttpSession session) {
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return redirectUtils.redirectAccess();
      }  
      String username = (String) session.getAttribute("entId");
      BaseResponseRTO response = cvFacade.uploadCurriculum(uploadCvTO);
      if(ObjectUtils.isEmpty(response.getError())) {        
         return redirectUtils.redirectHome(username);
      } 
      return redirectUtils.redirectInsert(username, response.getError(), uploadCvTO.getInterviewId());
   }

   /**
    * Download CV.
    *
    * @param interviewId the interview id
    * @param session the session
    * @return the response entity
    */
   @GetMapping(path = "/download/{cvId}")
   public @ResponseBody ResponseEntity<Object> downloadCV(@PathVariable("cvId")Long interviewId, HttpSession session) {
      if(ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      BaseResponseRTO response = cvFacade.downloadCurriculum(interviewId);
      if(ObjectUtils.isEmpty(response.getError())) {			
         DownloadFileRTO file = (DownloadFileRTO) response.getBody();
         return ResponseEntity.ok()
               .contentType(MediaType.APPLICATION_PDF)
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
               .body(new ByteArrayResource(file.getContent()));
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
   }




}
