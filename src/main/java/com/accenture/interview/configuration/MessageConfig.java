package com.accenture.interview.configuration;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * The Class MessageConfig.
 */
@Configuration
public class MessageConfig {
    

    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        final Properties properties = new Properties();
        properties.setProperty("fileEncodings", "UTF-8");
        messageSource.setFileEncodings(properties);
        return messageSource;
    }
    
    /**
     * Gets the validator.
     *
     * @return the validator
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
