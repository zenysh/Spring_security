package com.total.Security;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.total.Security.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localeresolver()
	{
		SessionLocaleResolver localresolver = new SessionLocaleResolver();
		localresolver.setDefaultLocale(Locale.US);
		return localresolver;
	}
	
	@Bean
	public ResourceBundleMessageSource bundleMessage()
	{
		ResourceBundleMessageSource RBMS = new ResourceBundleMessageSource();
		RBMS.setBasename("messages");
		return RBMS;
	}
}
