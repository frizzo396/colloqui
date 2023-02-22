package com.accenture.interview.controller2.interview;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.interview.facade.SearchInterviewFacade;
import com.accenture.interview.to.interview.SearchInterviewResponsibleTO;
import com.accenture.interview.to.interview.SearchInterviewTO;

/**
 * The Class SearchInterviewController.
 */
@RestController
@RequestMapping("/interview/search")
public class SearchInterviewController {

   /** The interview facade. */
   @Autowired
   private SearchInterviewFacade searchInterviewFacade;

   /**
    * Inits the search interviewer.
    *
    * @return the response entity
    */
   @GetMapping("/interviewer")
   public ResponseEntity<Object> initSearchInterviewer() {
      return new ResponseEntity<>(searchInterviewFacade.initSearchInterviewer(), HttpStatus.OK);
   }

   /**
    * Inits the search responsible.
    *
    * @return the response entity
    */
   @GetMapping("/responsible")
   public ResponseEntity<Object> initSearchResponsible() {
      return new ResponseEntity<>(searchInterviewFacade.initSearchResponsible(), HttpStatus.OK);
   }

   /**
    * Search interviewer.
    *
    * @param searchInterviewTO the search interview TO
    * @param session           the session
    * @return the response entity
    */
   @PostMapping("/interviewer")
   public ResponseEntity<Object> searchInterviewer(@RequestBody SearchInterviewTO searchInterviewTO, HttpSession session) {
      /*
       * if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
       * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
       */
      return new ResponseEntity<>(searchInterviewFacade.searchInterviewsInterviewer(searchInterviewTO), HttpStatus.OK);
   }

   /**
    * Search responsible.
    *
    * @param searchInterviewTO the search interview TO
    * @param session           the session
    * @return the response entity
    */
   @PostMapping("/responsible")
   public ResponseEntity<Object> searchResponsible(@RequestBody SearchInterviewResponsibleTO searchInterviewTO, HttpSession session) {
      /*
       * if (ObjectUtils.isEmpty(session.getAttribute("entId"))) {
       * return new ResponseEntity<>(HttpStatus.FORBIDDEN); }
       */
      return new ResponseEntity<>(searchInterviewFacade.searchInterviewsResponsible(searchInterviewTO), HttpStatus.OK);
   }

}