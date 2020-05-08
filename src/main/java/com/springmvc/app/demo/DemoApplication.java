package com.springmvc.app.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@ComponentScan("com.springmvc.app")
@EnableJpaRepositories("com.springmvc.app.repository")
@EntityScan("com.springmvc.app.beans")
public class DemoApplication {

	private static final Logger logger = LogManager.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		logger.info("Starting my application Himanshu");
		SpringApplication.run(DemoApplication.class, args);
		System.setProperty("log4j2.contextSelector",
				"org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
	}

	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeContextResolver = new AcceptHeaderLocaleResolver();
		localeContextResolver.setDefaultLocale(Locale.US);
		return localeContextResolver;
	}

	@Bean
	public ResourceBundleMessageSource resourceBundleMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
}
