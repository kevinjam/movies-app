package com.kevinjanvier.movies.services;

import com.kevinjanvier.movies.model.Movie;
import com.kevinjanvier.movies.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(Movie movie) {
       return movieRepository.save(movie);
    }

    public List<Movie> findAllMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieById(String id){
        return movieRepository.findById(id);
    }

    public Optional<Movie> updateMovie(String id,Movie movie){
      return   movieRepository.findById(id)
                .map(existingMovie ->{
                    existingMovie.setTitle(movie.getTitle());
                    existingMovie.setGenre(movie.getGenre());
                    existingMovie.setDirector(movie.getDirector());
                    existingMovie.setYear(movie.getYear());
                    return movieRepository.save(existingMovie);
                });
    }

    public void deleteById(String id){
        movieRepository.deleteById(id);
    }


}
