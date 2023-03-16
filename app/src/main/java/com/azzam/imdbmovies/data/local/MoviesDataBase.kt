package com.azzam.imdbmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azzam.imdbmovies.data.local.entity.MovieEntity


@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun getRunDao(): MoviesDao
}