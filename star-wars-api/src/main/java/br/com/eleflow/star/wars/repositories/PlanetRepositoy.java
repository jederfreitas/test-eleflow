package br.com.eleflow.star.wars.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import br.com.eleflow.star.wars.documents.Planet;
import br.com.eleflow.star.wars.projections.PlanetProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetRepositoy extends ReactiveMongoRepository<Planet, ObjectId> {

	public Mono<PlanetProjection> findOneById(ObjectId id);

	@Query(value = "{'name': ?0}", fields = "{}")
	public Flux<PlanetProjection> findByName(String name);

	@Query(value = "{}", fields = "{}")
	public Flux<PlanetProjection> findAllPlanets();

	public Mono<Void> removeByName(String name);
}
