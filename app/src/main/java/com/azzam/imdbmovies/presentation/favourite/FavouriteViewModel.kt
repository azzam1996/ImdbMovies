package com.azzam.imdbmovies.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val favoriteMoviesData = MutableStateFlow<List<Movie>>(mutableListOf())

    fun getFavoriteMovies() {
        viewModelScope.launch {
            moviesRepository.getFavoriteMovies().collect {
                favoriteMoviesData.value = it
            }
        }
    }
}