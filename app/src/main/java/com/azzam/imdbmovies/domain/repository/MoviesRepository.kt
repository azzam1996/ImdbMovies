package com.azzam.imdbmovies.domain.repository

import com.azzam.imdbmovies.domain.util.Result
import com.azzam.imdbmovies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMovies(): Result<List<Movie>?>
    suspend fun searchForMovie(expression: String): Result<List<Movie>?>

    fun getFavoriteMovies(): Flow<List<Movie>>
    fun insertMovie(movie: Movie)
}