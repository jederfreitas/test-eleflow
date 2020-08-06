package br.com.eleflow.star.wars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eleflow.star.wars.dto.PlanetSwApiResultDto;
import br.com.eleflow.star.wars.service.PlanetThirdPartyApiService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/swapi")
public class SwApiController {

	@Autowired
	private PlanetThirdPartyApiService swapiService;

	@GetMapping(value = "/page/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Mono<PlanetSwApiResultDto> findAll(@PathVariable("page") int page) {
		return swapiService.findAll(page);
	}

	@GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<PlanetSwApiResultDto> findByName(@PathVariable("name") String name) {
		return swapiService.search(name, 1);
	}
}
