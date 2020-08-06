package br.com.eleflow.star.wars.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.eleflow.star.wars.documents.Planet;
import br.com.eleflow.star.wars.dto.PlanetDto;
import br.com.eleflow.star.wars.projections.PlanetProjection;
import br.com.eleflow.star.wars.repositories.PlanetRepositoy;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class PlanetServiceTest {


	@Autowired
	private PlanetThirdPartyApiService awapi;

	@Autowired
	private PlanetService planetService;
	
	@Autowired
	private PlanetRepositoy planetRepositoy;


	@BeforeEach
	void setUp() throws Exception {
		planetRepositoy.deleteAll().block();
	}

	@Test
	void testFindById() {

		var planetDto = getPlanetDto();

		var savedPlanet = planetService.save(planetDto).block();

		var findById = planetService.findById(savedPlanet.getId());

		doPlanetDocumentAssertions(savedPlanet, findById);

	}

	@Test
	void testFindByName() {

		var planetDto = getPlanetDto();

		var savedPlanet = planetService.save(planetDto).block();

		var findByName = planetService.findByName(savedPlanet.getName());
		
		assertNotEquals(0, findByName.count().block());
		
		doPlanetDocumentAssertions(savedPlanet, findByName.next());

	}

	@Test
	void testFindAll() {

		var planetDto = getPlanetDto();

		var savedPlanet = planetService.save(planetDto).block();

		var findAll = planetService.findAll();

		assertNotEquals(0, findAll.count().block());
		
		doPlanetDocumentAssertions(savedPlanet, findAll.next());
	}

	@Test
	void testSave() {

		var planetDto = getPlanetDto();

		var savedPlanet = planetService.save(planetDto);

		var swPlanet = awapi.search(planetDto.getName(), 1).block();

		StepVerifier.create(savedPlanet).assertNext(planet -> {
			assertNotNull(planet.getId());
			assertEquals(planetDto.getName(), planet.getName());
			assertEquals(planetDto.getClimate(), planet.getClimate());
			assertEquals(planetDto.getTerrain(), planet.getTerrain());
			assertNotEquals(swPlanet.getResults().stream().findFirst().get().getFilms().size(),
					planetDto.getFilmsQuantity());
		}).expectComplete().verify();

	}

	@Test
	void testRemove() {

		var planetDto = getPlanetDto();

		var saved = planetService.save(planetDto).block();

		planetService.remove(saved.getId()).block();

		var findAll = planetService.findAll();

		assertEquals(0, findAll.count().block());

	}

	@Test
	void testRemoveByName() {
		
		var planetDto = getPlanetDto();

		planetService.save(planetDto).block();

		planetService.removeByName(planetDto.getName()).block();

		var findAll = planetService.findAll();
		
		assertEquals(0, findAll.count().block());
		
	}

	private PlanetDto getPlanetDto() {

		var planetDto = new PlanetDto();
		planetDto.setName("Tatooine");
		planetDto.setClimate("temperate, tropical");
		planetDto.setTerrain("jungle, rainforests");

		return planetDto;
	}

	private void doPlanetDocumentAssertions(Planet expectedPlanet, Mono<PlanetProjection> planetToCompare)
			throws AssertionError {
		StepVerifier.create(planetToCompare).assertNext(planet -> {
			assertEquals(expectedPlanet.getId().toString(), planet.getId().toString());
			assertEquals(expectedPlanet.getName(), planet.getName());
			assertEquals(expectedPlanet.getClimate(), planet.getClimate());
			assertEquals(expectedPlanet.getTerrain(), planet.getTerrain());
			assertNotEquals(expectedPlanet, planet.getFilmsQuantity());
		}).expectComplete().verify();
	}

}
