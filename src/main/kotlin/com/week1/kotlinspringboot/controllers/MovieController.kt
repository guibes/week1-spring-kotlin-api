package com.week1.kotlinspringboot.movie

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movie")
class MovieController(var movieService: MovieService) {

    @GetMapping
    fun findMovies(): Iterable<Movie> {
        return movieService.findMovies()
    }
}
