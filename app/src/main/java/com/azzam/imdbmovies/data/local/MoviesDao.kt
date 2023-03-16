package com.azzam.imdbmovies.data.local

import androidx.room.*
import com.azzam.imdbmovies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Transaction
    @Query("SELECT * FROM MovieEntity")
    fun getAllFavouriteMovies(): Flow<List<MovieEntity>>
}