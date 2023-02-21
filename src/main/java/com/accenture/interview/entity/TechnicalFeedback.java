package com.accenture.interview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.accenture.interview.to.feedback.CreateTechFeedbackTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class TechFeedback.
 */
@Entity(name = "Tech_feedback")
@Table(name = "technical_feedback")
public class TechnicalFeedback {

   /** The id. */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   /** The comment. */
   private String comment;

   /** The interview. */
   @OneToOne(mappedBy = "techFeedbackId")
   @JsonIgnore
   private Interview interview;

   /** The scores in JSON format */
   @Column(name = "scores")
   private String scores;

   /**
    * Instantiates a new tech feedback.
    */
   public TechnicalFeedback() {
   }

   /**
    * Instantiates a new tech feedback.
    *
    * @param createTechFeedbackRequest the create tech feedback
    *                                  request
    */
   public TechnicalFeedback(CreateTechFeedbackTO createTechFeedbackRequest) {
      this.comment = createTechFeedbackRequest.getComment();

      // NUOVO CAMPO PER FORMATO JSON
      this.scores = createTechFeedbackRequest.getScores();
   }

   public TechnicalFeedback(Long id, CreateTechFeedbackTO createTechFeedbackRequest) {
      this.id = id;
      this.comment = createTechFeedbackRequest.getComment();

      // NUOVO CAMPO PER FORMATO JSON
      this.scores = createTechFeedbackRequest.getScores();
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Interview getInterview() {
      return interview;
   }

   public void setInterview(Interview interview) {
      this.interview = interview;
   }
}
