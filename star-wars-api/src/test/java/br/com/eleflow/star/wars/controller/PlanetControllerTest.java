/**
 * 
 */
package br.com.eleflow.star.wars.controller;

import static br.com.eleflow.star.wars.api.TestUtils.getPlanetProjection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.com.eleflow.star.wars.documents.Planet;
import br.com.eleflow.star.wars.dto.PlanetDto;
import br.com.eleflow.star.wars.service.PlanetService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PlanetController.class)
public class PlanetControllerTest {

	@MockBean
	private PlanetService service;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private WebTestClient webClient;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreate() {

		var planetDto = getPlanetDto();

		var planetEntity = modelMapper.map(planetDto, Planet.class);

		planetEntity.setId(new ObjectId());

		Mockito
			.when(service.save(planetDto)).thenReturn(Mono.just(planetEntity));
		
		webClient.post()
				.uri("/planets")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(planetEntity))
				.exchange()
				.expectStatus().isCreated();

		Mockito.verify(service, times(1)).save(planetDto);

	}

	private PlanetDto getPlanetDto() {
		var planetDto = new PlanetDto();

		planetDto.setClimate("arid");
		planetDto.setFilmsQuantity(6);
		planetDto.setName("Tatooine");
		planetDto.setTerrain("desert");
		return planetDto;
	}

	@Test
	void testFindAll() {
		
		var planetDto = getPlanetDto();

		var planetEntity = getPlanetProjection(planetDto);
		
        var planetFlux = Flux.fromIterable(Arrays.asList(planetEntity));
         
        Mockito
            .when(service.findAll())
            .thenReturn(planetFlux);
 
        webClient.get().uri("/planets")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(PlanetDto.class)
            .consumeWith(result -> {
            	assertEquals(planetDto, result.getResponseBody().stream().findFirst().get());
            });
         
        Mockito.verify(service, times(1)).findAll();
	}

	@Test
	void testFindById() {
		
		var id = new ObjectId();
		
		var planetDto = getPlanetDto();

		var planetEntity = getPlanetProjection(planetDto);
		
        var planetMono = Mono.just(planetEntity);
         
        Mockito
            .when(service.findById(id))
            .thenReturn(planetMono);
         
        
		webClient.get().uri("/planets/{id}", id)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBody(PlanetDto.class)
            .consumeWith(result -> {
            	assertEquals(planetDto, result.getResponseBody());
            });
         
        Mockito.verify(service, times(1)).findById(id);
        
	}

	@Test
	void testFindByName() {
        
		var planetDto = getPlanetDto();

		var planetEntity = getPlanetProjection(planetDto);
		
        var planetFlux = Flux.fromIterable(Arrays.asList(planetEntity));
         
        Mockito
            .when(service.findByName("Test"))
            .thenReturn(planetFlux);
 
        webClient.get().uri("/planets/name/{name}", "Test")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(PlanetDto.class)
            .consumeWith(result -> {
            	assertEquals(planetDto, result.getResponseBody().stream().findAny().get());
            });
         
        Mockito.verify(service, times(1)).findByName("Test");
        
	}

	@Test
	void testDelete() {

		Mono<Void> voidReturn = Mono.empty();

		var planetId = new ObjectId();

		Mockito
			.when(service.remove(planetId))
			.thenReturn(voidReturn);

		webClient
			.delete()
			.uri("/planets/{id}", planetId)
			.exchange()
			.expectStatus().isOk();
		
		Mockito.verify(service, times(1)).remove(planetId);
	}

	@Test
	void testDeleteByName() {
		Mono<Void> voidReturn = Mono.empty();


		Mockito
			.when(service.removeByName("Test"))
			.thenReturn(voidReturn);

		webClient
			.delete()
			.uri("/planets/name/{id}", "Test")
			.exchange()
			.expectStatus().isOk();
		
		Mockito.verify(service, times(1)).removeByName("Test");
		
	}

}
