package br.com.eleflow.star.wars.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetSwApiResultDto {

	private int count;
	private String next;
	private String previous;
	private List<PlanetSwApiDto> results;
}
