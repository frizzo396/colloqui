package com.accenture.interview.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * The Class AsyncConfig.
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig {

   /** The Constant MAIL_THREAD. */
   private static final String MAIL_THREAD = "MAIL_THREAD";


   /**
    * Task executor.
    *
    * @return the executor
    */
   @Bean
   public Executor taskExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(2);
      executor.setMaxPoolSize(10);
      executor.setQueueCapacity(500);
      executor.setAwaitTerminationSeconds(1200);
      executor.setThreadNamePrefix(MAIL_THREAD);
      executor.initialize();
      return executor;
   }
}