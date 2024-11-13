package com.WaitingList.BACKEND;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BackendApplication.class, args);

		logger.info("Starting to scan for controllers...");

		String[] beanNames = ctx.getBeanDefinitionNames();
		logger.info("Total beans found: {}", beanNames.length);

		for (String beanName : beanNames) {
			if (beanName.contains("Controller")) {
				logger.info("Found controller: {}", beanName);
			}
		}

		logger.info("Finished scanning for controllers.");
	}
}