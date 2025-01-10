package com.kevinjanvier.movies.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinjanvier.movies.model.Movie;
import com.kevinjanvier.movies.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
//TODO change all that to test container after homework is done
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;
    private File file;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        file = ResourceUtils.getFile("classpath:MockExpectations.json");
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void testAddMovie() throws Exception {
        var jsonContent = new String(Files.readAllBytes(file.toPath()));
        Movie movie = new Movie("Inception", "Sci-Fi", "Christopher Nolan", 2025);
        when(movieService.saveMovie(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"))
                .andExpect(jsonPath("$.year").value(2025));

        verify(movieService, times(1)).saveMovie(any(Movie.class));
    }

    @Test
    public void testGetAllMovies() throws Exception {
        List<Movie> movies = readMoviesFromJson("classpath:dummy-movies.json");
        when(movieService.findAllMovies()).thenReturn(movies);
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].title").value("Titanic"));

        verify(movieService, times(1)).findAllMovies();
    }

    @Test
    public void testGetMovieById() throws Exception {
        Movie movie = readMoviesFromJson("classpath:dummy-movies.json").getFirst();
        when(movieService.findMovieById("1")).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.director").value("Kevin"))
                .andExpect(jsonPath("$.year").value(2010));
        verify(movieService, times(1)).findMovieById("1");
    }

    @Test
    public void testGetMovieByIdNotFound() throws Exception {
        when(movieService.findMovieById("1")).thenReturn(Optional.empty());
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
        verify(movieService, times(1)).findMovieById("1");
    }


    @Test
    public void testDeleteMovie() throws Exception {
        doNothing().when(movieService).deleteById("1");
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
        verify(movieService, times(1)).deleteById("1");
    }

    private List<Movie> readMoviesFromJson(String filePath) throws Exception {
        String jsonContent = new String(Files.readAllBytes(ResourceUtils.getFile(filePath).toPath()));
        return Arrays.asList(objectMapper.readValue(jsonContent, Movie[].class));
    }
}
