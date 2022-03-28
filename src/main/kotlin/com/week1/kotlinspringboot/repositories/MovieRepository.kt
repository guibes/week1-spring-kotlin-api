package com.week1.kotlinspringboot.movie

import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<Movie, Long> {}
