package com.kevinjanvier.movies;

import com.kevinjanvier.movies.model.Movie;
import com.kevinjanvier.movies.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final MovieRepository movieRepository;

    public Application(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		movieRepository.deleteAll();
		movieRepository.saveAll(List.of(
				new Movie("The Shawshank Redemption", "Drama", "Frank Darabont", 1994),
				new Movie("The Godfather", "Crime", "Francis Ford Coppola", 1972),
				new Movie("The Dark Knight", "Action", "Christopher Nolan", 2008),
				new Movie("Inception", "Sci-Fi", "Christopher Nolan", 2010),
				new Movie("Forrest Gump", "Drama", "Robert Zemeckis", 1994),
				new Movie("Fight Club", "Drama", "David Fincher", 1999),
				new Movie("Pulp Fiction", "Crime", "Quentin Tarantino", 1994),
				new Movie("The Matrix", "Sci-Fi", "Lana Wachowski, Lilly Wachowski", 1999),
				new Movie("Interstellar", "Sci-Fi", "Christopher Nolan", 2014),
				new Movie("The Lord of the Rings: The Return of the King", "Fantasy", "Peter Jackson", 2003)
		));	}
}
