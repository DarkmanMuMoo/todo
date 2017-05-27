package com.mumoo;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	@Autowired
	private Environment environment;

	@PostConstruct
	public void initApplication() {
		if (this.environment.getActiveProfiles().length == 0) {
			LOGGER.warn("No Spring profile configured, running with default configuration");
		} else {
			LOGGER.info("Running with Spring profile(s) : {}", Arrays.toString(this.environment.getActiveProfiles()));
		}
	}

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
