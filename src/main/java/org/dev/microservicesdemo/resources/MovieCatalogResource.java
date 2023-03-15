package org.dev.microservicesdemo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.dev.microservicesdemo.models.CatalogItem;
import org.dev.microservicesdemo.models.UserRating;
import org.dev.microservicesdemo.services.MovieInfo;
import org.dev.microservicesdemo.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private MovieInfo movieInfo;

	@Autowired
	private UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCalalog(@PathVariable("userId") String userId) {
		UserRating ratings = userRatingInfo.getUserRating(userId);
		return ratings.getUserRatings().stream().map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
	}

}
