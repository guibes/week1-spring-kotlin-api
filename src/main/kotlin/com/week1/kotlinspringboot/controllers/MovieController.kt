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

    @PostMapping
    fun createMovie(@RequestBody movie: Movie): Movie {
        return movieService.createMovie(movie)
    }

    @PatchMapping("{id}")
    fun updateMovie(@PathVariable id: Long, @RequestBody updateMovie: UpdateMovie): Movie {
        return movieService.updateMovie(id, updateMovie.title)
    }

    @DeleteMapping("{id}")
    fun deleteMovie(@PathVariable id: Long): String {
        return movieService.deleteMovie(id)
    }
}
