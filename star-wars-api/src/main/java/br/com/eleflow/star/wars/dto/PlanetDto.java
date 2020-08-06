package br.com.eleflow.star.wars.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetDto {

	private String name;
	private String climate;
	private String terrain;
	private int filmsQuantity;
}
