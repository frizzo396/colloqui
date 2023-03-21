package com.accenture.interview.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * The Class EmailConfig.
 */
@Configuration
public class EmailConfig {

    /** The host. */
    @Value("${aws.ses.host}")
    private String host;

    /** The user. */
    @Value("${aws.ses.user}")
    private String user;

    /** The password. */
    @Value("${aws.ses.password}")
    private String password;
    
    @Value("${aws.ses.port}")
    private String port;
	
	/**
	 * Gets the java mail sender.
	 *
	 * @return the java mail sender
	 */
   @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set up config
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(user);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.default-encoding", "UTF-8");      
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", Integer.parseInt(port));
        props.put("mail.smtp.starttls.required", "true");
        return mailSender;
    }

}
