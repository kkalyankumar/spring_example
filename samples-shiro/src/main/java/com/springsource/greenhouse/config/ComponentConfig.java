package com.springsource.greenhouse.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages="com.springsource.greenhouse")
public class ComponentConfig {

	@Configuration
	@Profile("embedded")
	@PropertySource("classpath:com/springsource/greenhouse/config/embedded.properties")
	static class Embedded {
	}
	
}
