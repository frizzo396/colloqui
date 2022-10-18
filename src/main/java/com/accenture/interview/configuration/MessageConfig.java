package com.accenture.interview.configuration;

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
    
    /** The Constant MESSAGE_SOURCE_PATH. */
    private static final String MESSAGE_SOURCE_PATH = "classpath:/messages";

    /**
     * Message source.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE_PATH);
        messageSource.setDefaultEncoding("UTF-8");
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
