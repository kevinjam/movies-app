package com.kevinjanvier.movies.repositories;

import com.kevinjanvier.movies.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
