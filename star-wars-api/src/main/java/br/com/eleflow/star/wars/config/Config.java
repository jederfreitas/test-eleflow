package br.com.eleflow.star.wars.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class Config {

	@Value("${start.wars.api.url}")
    private String apiBaseUrl;

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
