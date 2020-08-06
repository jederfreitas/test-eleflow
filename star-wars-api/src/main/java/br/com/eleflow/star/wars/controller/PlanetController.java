package br.com.eleflow.star.wars.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eleflow.star.wars.documents.Planet;
import br.com.eleflow.star.wars.dto.PlanetDto;
import br.com.eleflow.star.wars.projections.PlanetProjection;
import br.com.eleflow.star.wars.service.PlanetService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/planets")
public class PlanetController {

	@Autowired
	private PlanetService planetService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Planet> create(@RequestBody PlanetDto planet) {
		return planetService.save(planet);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<PlanetProjection> findAll() {
		return planetService.findAll();
	}

	@GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
	public Mono<PlanetProjection> findById(@PathVariable("id") String id) {
		return planetService.findById(new ObjectId(id));
	}
	
	@GetMapping(value = "/name/{name}", produces =  MediaType.APPLICATION_JSON_VALUE)
	public Flux<PlanetProjection> findByName(@PathVariable("name") String name) {
		return planetService.findByName(name);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return planetService.remove(new ObjectId(id));
	}
	
	@DeleteMapping(value = "/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Void> deleteByName(@PathVariable("name") String name) {
		return planetService.removeByName(name);
	}
}
