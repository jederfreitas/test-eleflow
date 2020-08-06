package br.com.eleflow.star.wars.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetSwApiDto {

	private String name;
	private String climate;
	private String terrain;
	private List<String> films;
}
