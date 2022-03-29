package com.week1.kotlinspringboot.movie

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MovieService(var movieRepository: MovieRepository) {

    fun findMovies(): Iterable<Movie> {
        return movieRepository.findAll()
    }

    fun createMovie(movie: Movie): Movie {
        return movieRepository.save(movie)
    }

    fun updateMovie(id: Long, title: String): Movie {
        var movie: Movie =
                movieRepository.findById(id).orElseThrow {
                    ResponseStatusException(HttpStatus.NOT_FOUND, "movie does not exists")
                }
        movie.title = title
        return movieRepository.save(movie)
    }
}
