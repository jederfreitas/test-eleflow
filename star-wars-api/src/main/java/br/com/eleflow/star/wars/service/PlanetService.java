package br.com.eleflow.star.wars.service;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eleflow.star.wars.documents.Planet;
import br.com.eleflow.star.wars.dto.PlanetDto;
import br.com.eleflow.star.wars.dto.PlanetSwApiResultDto;
import br.com.eleflow.star.wars.projections.PlanetProjection;
import br.com.eleflow.star.wars.repositories.PlanetRepositoy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetService {

	@Autowired
	private PlanetRepositoy planetRepository;

	@Autowired
	private PlanetThirdPartyApiService swapi;
	
	@Autowired
    private ModelMapper modelMapper;
	
	public Mono<PlanetProjection> findById(ObjectId id) {
		return planetRepository.findOneById(id);
	}

	public Flux<PlanetProjection> findByName(String name) {
		return planetRepository.findByName(name);
	}

	public Flux<PlanetProjection> findAll() {
		return planetRepository.findAllPlanets();
	}

	public Mono<Planet> save(PlanetDto planet) {

		var planetEntity = modelMapper.map(planet, Planet.class);
		
		return swapi.search(planet.getName(), 1).doOnSuccess(consumer -> {
			setMoviesCount(planetEntity, consumer);
		}).then(planetRepository.save(planetEntity));
	}

	private void setMoviesCount(Planet planetEntity, PlanetSwApiResultDto consumer) {
		if (consumer.getCount() > 0) {
			planetEntity.setFilmsQuantity(consumer.getResults().stream().findFirst().get().getFilms().size());
		}
	}

	public Mono<Void> remove(ObjectId planetId)
	{
		return planetRepository.deleteById(planetId);
	}
	
	public Mono<Void> removeByName(String name)
	{
		return planetRepository.removeByName(name);
	}

}
