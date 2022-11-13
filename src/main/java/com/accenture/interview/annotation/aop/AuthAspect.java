package com.accenture.interview.annotation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.interview.rto.interviewer.InterviewerRTO;
import com.accenture.interview.service.InterviewerService;
import com.accenture.interview.to.interviewer.RequestRegistrationTO;

/**
 * The Class AuthAspect.
 */
@Aspect
@Component
public class AuthAspect {
	
	/** The interviewer service. */
	@Autowired
	private InterviewerService interviewerService;
	

	/**
	 * Check user registered.
	 *
	 * @param joinPoint the join point
	 * @return the object
	 * @throws Throwable the throwable
	 */
	@Around("@annotation(com.accenture.interview.annotation.Registered)")
	public Object checkUserRegistered(ProceedingJoinPoint joinPoint) throws Throwable {
		ModelAndView modelAndView = new ModelAndView();
		String username = System.getProperty("user.name") /*"ent.id"*/;
		InterviewerRTO interviewer = interviewerService.findInterviewerByEnterpriseId(username);

		if(ObjectUtils.isEmpty(interviewer)) {
			modelAndView.addObject("enterpriseId", username);
			modelAndView.addObject("requestRegistrationTO", new RequestRegistrationTO());
			modelAndView.setViewName("error-page.html");
			return modelAndView;
		}		
	    return joinPoint.proceed();
	}

}
