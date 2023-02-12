package com.accenture.interview.controller.hiring;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.HiringFacade;
import com.accenture.interview.rto.general.BaseResponseRTO;
import com.accenture.interview.utils.constants.PaginationConstants;

/**
 * The Class HiringController.
 */
@RestController
@RequestMapping("/hiring")
public class HiringController {


   /** The hiring facade. */
   @Autowired
   private HiringFacade hiringFacade;
    

   /**
    * Hiring candidates list.
    *
    * @param session the session
    * @return the response entity
    */
   @GetMapping("/candidates")
   public ResponseEntity<BaseResponseRTO> hiringCandidatesList(HttpSession session) {
      if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
         return new ResponseEntity<>(new BaseResponseRTO(null, PaginationConstants.EXPIRED), HttpStatus.OK);
      }
      return new ResponseEntity<>(new BaseResponseRTO(hiringFacade.getHiringCandidates()), HttpStatus.OK);
   }   
   
}
