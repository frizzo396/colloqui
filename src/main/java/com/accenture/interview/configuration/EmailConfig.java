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
	
    /** The Constant GMAIL_SMTP_PORT. */
    private static final int GMAIL_SMTP_PORT = 587;

    /** The host. */
    @Value("${spring.mail.host}")
    private String host;

    /** The user. */
    @Value("${spring.mail.username}")
    private String user;

    /** The password. */
    @Value("${spring.mail.password}")
    private String password;
    
	
	/**
	 * Gets the java mail sender.
	 *
	 * @return the java mail sender
	 */
	@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Set up Gmail config
        mailSender.setHost(host);
        mailSender.setPort(GMAIL_SMTP_PORT);
        mailSender.setUsername(user);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.default-encoding", "UTF-8");      
        return mailSender;
    }

}
