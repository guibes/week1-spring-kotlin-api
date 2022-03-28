package com.week1.kotlinspringboot.movie

import org.springframework.stereotype.Service

@Service
class MovieService(var movieRepository: MovieRepository) {

    fun findMovies(): Iterable<Movie> {
        return movieRepository.findAll()
    }
}
