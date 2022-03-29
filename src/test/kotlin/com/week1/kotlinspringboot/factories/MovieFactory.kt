import com.github.javafaker.Faker
import com.week1.kotlinspringboot.movie.*
import io.github.bluegroundltd.kfactory.*

class MovieFactory : Factory<Movie> {
        override fun produce(): Movie =
                        Movie(
                                        title = Faker().name().nameWithMiddle(),
                                        overview = Faker().lorem().paragraph(),
                                        genre = Faker().lorem().word(),
                                        posterPath = Faker().internet().url(),
                        )
}
