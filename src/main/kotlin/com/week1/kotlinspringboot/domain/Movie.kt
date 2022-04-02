package com.week1.kotlinspringboot.movie

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "movies")
data class Movie(
                var title: String,
                var overview: String,
                var genre: String,
                var posterPath: String,
                @Id @GeneratedValue var id: Long? = null
)
