package com.week1.kotlinspringboot.movie

import MovieFactory
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
        @Autowired lateinit var mockMvc: MockMvc
        @Autowired lateinit var movieService: MovieService
        @Autowired lateinit var movieRepository: MovieRepository

        @Test
        fun `@GET movies route should return a empty list if does not exist movies`() {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/"))
                                .andExpect(MockMvcResultMatchers.status().isOk)
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                )
                                .andExpect(MockMvcResultMatchers.content().string("[]"))
        }

        @Test
        fun `@GET movies route should return a movie if exists one in list`() {
                var movie: Movie = MovieFactory().produce()
                var createdMovie: List<Movie> = listOf(movieService.createMovie(movie))

                mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/"))
                                .andExpect(MockMvcResultMatchers.status().isOk)
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                )
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .string(
                                                                                ObjectMapper().writeValueAsString(
                                                                                                                createdMovie
                                                                                                )
                                                                )
                                )

                movieRepository.delete(movie)
        }

        @Test
        fun `@GET movies by id route should return a error if does not exists movies`() {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/100"))
                                .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

        @Test
        fun `@GET movies route should return a movie if exists one by that id`() {

                var movie: Movie = MovieFactory().produce()
                var createdMovie: Movie = movieService.createMovie(movie)

                var movieId: Long = createdMovie.id ?: 1

                mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/" + movieId))
                                .andExpect(MockMvcResultMatchers.status().isOk)
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                )

                movieRepository.delete(movie)
        }

        @Test
        fun `@POST create movie need throw an bad request a movie if the body was missing`() {
                mockMvc.perform(MockMvcRequestBuilders.post("/api/movies"))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest)
        }

        @Test
        fun `@POST create movie need create a movie if all params are right`() {
                var movie: Movie = MovieFactory().produce()
                movie.id = 3
                mockMvc.perform(
                                                MockMvcRequestBuilders.post("/api/movies")
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                                                .header(
                                                                                HttpHeaders.ACCEPT,
                                                                                MediaType.APPLICATION_JSON_VALUE
                                                                )
                                                                .content(
                                                                                ObjectMapper().writeValueAsString(
                                                                                                                movie
                                                                                                )
                                                                )
                                )
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk)
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                )
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .string(
                                                                                ObjectMapper().writeValueAsString(
                                                                                                                movie
                                                                                                )
                                                                )
                                )
                movieRepository.delete(movie)
        }

        @Test
        fun `@PATCH update movie need throw an bad request a movie if the body was missing`() {
                mockMvc.perform(MockMvcRequestBuilders.patch("/api/movies/999"))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest)
        }

        @Test
        fun `@PATCH update movie need throw a not found if movie does not exists and have a body`() {
                mockMvc.perform(
                                                MockMvcRequestBuilders.patch("/api/movies/999")
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                                                .content("{\"title\": \"test\"}")
                                )
                                .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

        @Test
        fun `@PATCH update movie need update a movie if all the params are right`() {
                val movie: Movie = MovieFactory().produce()
                val createdMovie: Movie = movieRepository.save(movie)
                mockMvc.perform(
                                                MockMvcRequestBuilders.patch(
                                                                                "/api/movies/" +
                                                                                                createdMovie.id
                                                                )
                                                                .contentType(
                                                                                MediaType.APPLICATION_JSON
                                                                )
                                                                .content("{\"title\": \"test\"}")
                                )
                                .andExpect(MockMvcResultMatchers.status().isOk)
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .json(
                                                                              "{\"title\":\"test\",\"overview\":\"" +
                                                                                                movie.overview +
                                                                                                "\",\"genre\":\"" +
                                                                                                movie.genre +
                                                                                                "\",\"posterPath\":\"" +
                                                                                                movie.posterPath +
                                                                                                "\",\"id\":" +
                                                                                                movie.id +
                                                                                                "}"
                                                                )
                                )
        }

        @Test
        fun `@DELETE delete movie need throw an not found if the movie does not exists was missing`() {
                val movie: Movie = MovieFactory().produce()
                val createdMovie: Movie = movieRepository.save(movie)
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/movies/" + createdMovie.id))
                                .andExpect(MockMvcResultMatchers.status().isOk)
        }

}
