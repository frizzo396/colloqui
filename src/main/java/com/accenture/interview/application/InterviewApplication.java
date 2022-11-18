package com.accenture.interview.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Class Interview.
 */
@SpringBootApplication(scanBasePackages = "com.accenture.interview.*", exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.accenture.interview.repository")
@EntityScan(basePackages = "com.accenture.interview.entity")
public class InterviewApplication {


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}

}
