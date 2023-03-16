package com.azzam.imdbmovies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import com.azzam.imdbmovies.domain.util.Result

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    var moviesList = mutableListOf<Movie>()
    val movieData = MutableStateFlow<List<Movie>?>(mutableListOf())
    val loading = MutableStateFlow(false)
    val error = MutableStateFlow(false)


    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true

            when (val response = moviesRepository.getMovies()) {
                is Result.Success -> {
                    response?.value?.let {
                        moviesList.clear()
                        moviesList.addAll(it.toMutableList())
                        processResponse(it)
                    }
                }
                is Result.Error -> {
                    error.value = true
                }
            }
            loading.value = false
        }
    }


    fun searchForMovie(expression: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true

            when (val response = moviesRepository.searchForMovie(expression)) {
                is Result.Success -> {
                    processResponse(response.value)
                }
                is Result.Error -> {
                    error.value = true
                }
            }

            loading.value = false
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.insertMovie(movie)
        }
    }

    private fun processResponse(items: List<Movie>?) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getFavoriteMovies().collect { favoriteMovies ->
                val result = mutableListOf<Movie>()
                items?.let { result.addAll(it) }
                Timber.v("Collect Called")

                favoriteMovies.forEach {
                    result.remove(it)
                }
                items?.mapIndexed { index, item ->
                    var newItem = item
                    val isFavorite = favoriteMovies.find { it.id == newItem?.id }
                    isFavorite?.let {
                        result.remove(item)
                        newItem = newItem.copy(isFavorite = true)
                        result.add(index, newItem)
                    }
                }
                Timber.v("favoriteMovies ${favoriteMovies.size}")
                loading.value = false
                movieData.value = result

            }
        }
    }
}