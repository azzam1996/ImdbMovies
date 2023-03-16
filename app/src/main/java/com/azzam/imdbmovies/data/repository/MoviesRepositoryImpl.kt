package com.azzam.imdbmovies.data.repository

import com.azzam.imdbmovies.data.local.MoviesDao
import com.azzam.imdbmovies.data.mappers.toMovieEntity
import com.azzam.imdbmovies.data.mappers.toMoviesList
import com.azzam.imdbmovies.data.remote.Api
import com.azzam.imdbmovies.data.remote.safeApiCall
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.repository.MoviesRepository
import com.azzam.imdbmovies.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(private val api: Api, private val moviesDao: MoviesDao) :
    MoviesRepository {

    override suspend fun getMovies(): Result<List<Movie>?> {
        return when (val response = safeApiCall { api.getMovies() }) {
            is Result.Success -> {
                Result.Success(response.value?.items?.toMoviesList())
            }
            is Result.Error -> {
                Result.Error(response.code, response.errorBody)
            }
        }
    }

    override suspend fun searchForMovie(expression: String): Result<List<Movie>?> {
        return when (val response = safeApiCall { api.searchForMovie(expression) }) {
            is Result.Success -> {
                Result.Success(response.value?.results?.toMoviesList())
            }
            is Result.Error -> {
                Result.Error(response.code, response.errorBody)
            }
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return moviesDao.getAllFavouriteMovies()
            .map { it.toMoviesList() }
    }

    override fun insertMovie(movie: Movie) {
        moviesDao.insertMovie(movie.toMovieEntity())
    }
}