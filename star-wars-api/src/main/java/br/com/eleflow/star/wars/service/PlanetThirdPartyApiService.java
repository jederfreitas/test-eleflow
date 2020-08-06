package br.com.eleflow.star.wars.service;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.eleflow.star.wars.dto.PlanetSwApiResultDto;
import reactor.core.publisher.Mono;

@Service
public class PlanetThirdPartyApiService {

     
	@Value("${start.wars.api.url}")
    private String apiBaseUrl;

	
    public Mono<PlanetSwApiResultDto> findAll(int page) 
    {
    	return search("", page);
    }
    
    public Mono<PlanetSwApiResultDto> search(String searchQuery, int page) 
    {
    	String format = formatUrl(searchQuery, page);
		
    	return WebClient.create(format)
                .get()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PlanetSwApiResultDto.class);
    }

	private String formatUrl(String searchQuery, int page) {

		return !searchQuery.isEmpty() ? MessageFormat.format("{0}/?search={1}&page={2}", apiBaseUrl, searchQuery, page)
				: MessageFormat.format("{0}/?page={1}", apiBaseUrl, page);
	}
}
