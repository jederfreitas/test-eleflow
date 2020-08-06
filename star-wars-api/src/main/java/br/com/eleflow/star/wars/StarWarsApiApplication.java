package br.com.eleflow.star.wars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="br.com.eleflow.star.wars")
public class StarWarsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApiApplication.class, args);
	}

}
