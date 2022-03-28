import com.week1.kotlinspringboot.movie.*
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MovieServiceTest {
    val movieRepository: MovieRepository = mockk()
    val movieService: MovieService = MovieService(movieRepository)

    @Test
    fun `should call its data source to retrieve movies`() {
        var list: List<Movie> = emptyList()
        // given
        every { movieRepository.findAll() } returns list
        // where
        var result = movieService.findMovies()
        // then
        verify(exactly = 1) { movieRepository.findAll() }
        assertEquals(result, list)
    }
}
