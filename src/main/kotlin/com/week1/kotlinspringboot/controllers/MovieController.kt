package com.week1.kotlinspringboot.movie

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movies")
class MovieController(var movieService: MovieService) {

    @GetMapping
    fun findMovies(): Iterable<Movie> {
        return movieService.findMovies()
    }

    @GetMapping("{id}")
    fun findMovieById(@PathVariable id: Long): Movie {
        return movieService.findMovieById(id)
    }
}
