package com.week1.kotlinspringboot.movie

import MovieFactory
import com.github.javafaker.Faker
import com.week1.kotlinspringboot.movie.*
import io.mockk.*
import java.util.Optional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class MovieServiceTest {
    val movieRepository: MovieRepository = mockk()
    val movieService: MovieService = MovieService(movieRepository)

    @Test
    fun `findMovies() should call its data source to retrieve empty list of movies`() {
        var list: List<Movie> = emptyList()
        // given
        every { movieRepository.findAll() } returns list
        // where
        var result = movieService.findMovies()
        // then
        verify(exactly = 1) { movieRepository.findAll() }
        assertEquals(result, list)
    }

    @Test
    fun `findMovies() should call its data source to retrieve a populated list of movies`() {
        var list: List<Movie> = MovieFactory().produceMany().take(5).toList()
        // given
        every { movieRepository.findAll() } returns list
        // where
        var result = movieService.findMovies()
        // then
        verify(exactly = 1) { movieRepository.findAll() }
        assertEquals(result, list)
    }

    @Test
    fun `createMovie(movie) should call its method and need create a movie`() {
        var movie: Movie = MovieFactory().produce()
        // given
        every { movieRepository.save(movie) } returns movie
        // where
        var result = movieService.createMovie(movie)
        // then
        verify(exactly = 1) { movieRepository.save(movie) }
        assertEquals(result, movie)
    }

    @Test
    fun `updateMovie(id, title) should call its method and need update a movie's title`() {
        var testTitle: String = Faker().name().nameWithMiddle()
        var movie: Movie = MovieFactory().produce()
        var resultMovie: Movie = movie
        var id: Long = movie.id ?: -1
        resultMovie.title = testTitle
        // given
        every { movieRepository.findById(id) } returns Optional.of(movie)
        every { movieRepository.save(movie) } returns resultMovie
        // where
        var result = movieService.updateMovie(id, testTitle)
        // then
        verify(exactly = 1) { movieRepository.save(movie) }
        assertEquals(result, resultMovie)
    }

    @Test
    fun `updateMovie(id, title) should call its method and need throw an Not Found Exception`() {
        var id: Long = -1
        // given
        every { movieRepository.findById(id) } returns Optional.ofNullable(null)
        // then
        val exception =
                assertThrows(ResponseStatusException::class.java) {
                    movieService.updateMovie(id, "title")
                }
        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `deleteMovie(id) should call its method and need delete the movie`() {
        var id: Long = 123
        // given
        every { movieRepository.deleteById(id) } returns Unit
        // where
        movieService.deleteMovie(id)
        // then
        verify(exactly = 1) { movieRepository.deleteById(id) }
    }

    @Test
    fun `findMovieById(id) should call its method and need throw an exception if the movie does not exist`() {
        var id: Long = -1
        // given
        every { movieRepository.findById(id) } returns Optional.ofNullable(null)
        // then
        val exception =
                assertThrows(ResponseStatusException::class.java) { movieService.findMovieById(id) }
        assertEquals(HttpStatus.NOT_FOUND, exception.status)
    }

    @Test
    fun `findMovieById(id) should call its method and need return an movie`() {
        var movie: Movie = MovieFactory().produce()
        var id: Long = movie.id ?: -1
        // given
        every { movieRepository.findById(id) } returns Optional.of(movie)
        // where
        val result = movieService.findMovieById(id)
        // then
        verify(exactly = 1) { movieRepository.findById(id) }
        assertEquals(result, movie)
    }
}
