package com.week1.kotlinspringboot.movie

import MovieFactory
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
        @Autowired lateinit var mockMvc: MockMvc
        @Autowired lateinit var movieService: MovieService

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

        // @Test
        // fun `@GET movies route should return a movie if exists one in list`() {
        //         var objectMapper: ObjectMapper =
        //                         ObjectMapper().registerModule(JavaTimeModule())
        //                                         .configure(
        //                                                         SerializationFeature
        //
        // .WRITE_DATES_AS_TIMESTAMPS,
        //                                                         false
        //                                         )
        //         var movie: Movie = MovieFactory().produce()
        //         var createdMovie: List<Movie> = listOf(movieService.createMovie(movie))

        //         mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/"))
        //                         .andExpect(MockMvcResultMatchers.status().isOk)
        //                         .andExpect(
        //                                         MockMvcResultMatchers.content()
        //                                                         .contentType(
        //
        // MediaType.APPLICATION_JSON
        //                                                         )
        //                         )
        //                         .andExpect(
        //                                         MockMvcResultMatchers.content()
        //                                                         .string(
        //
        // objectMapper.writeValueAsString(
        //
        // createdMovie
        //                                                                         )
        //                                                         )
        //                         )

        //         movieService.deleteMovie(createdMovie[0].id ?: 1)
        // }

        @Test
        fun `@GET movies by id route should return a error if does not exists movies`() {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/100"))
                                .andExpect(MockMvcResultMatchers.status().isNotFound)
        }

        @Test
        fun `@GET movies route should return a movie if exists one in list`() {

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

                movieService.deleteMovie(movieId)
        }
}
