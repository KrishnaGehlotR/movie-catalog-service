package org.dev.microservicesdemo.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.dev.microservicesdemo.models.CatalogItem;
import org.dev.microservicesdemo.models.Movie;
import org.dev.microservicesdemo.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalogwebclient")
public class MovieCatalogResourceWebClient {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCalalog(@PathVariable("userId") String userId) {

		List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("5678", 3));

		// Using WebClient.Builder
		return ratings.stream().map(rating -> {

			Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve().bodyToMono(Movie.class).block();

			return new CatalogItem(movie.getName(), "Test", rating.getRating());

		}).collect(Collectors.toList());
	}
}
