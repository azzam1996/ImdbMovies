package com.azzam.imdbmovies.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.azzam.imdbmovies.R
import com.azzam.imdbmovies.databinding.FragmentSearchBinding
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.util.collectLatestLifecycleFlow
import com.azzam.imdbmovies.presentation.dialogs.DisplayMovieDialog
import com.azzam.imdbmovies.presentation.movies.MoviesAdapter
import com.azzam.imdbmovies.presentation.movies.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), MoviesAdapter.OnMovieClickedListener,
    MoviesAdapter.OnAddToFavoriteClickedListener {

    private val moviesViewModel by viewModel<MoviesViewModel>()

    private lateinit var _moviesBinding: FragmentSearchBinding

    private val moviesAdapter = MoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _moviesBinding = FragmentSearchBinding.bind(view)
        initAdapter()
        observeViewModel()

        _moviesBinding.ivSearch.setOnClickListener {
            moviesViewModel.searchForMovie(_moviesBinding.etSearch.text.toString())
        }

    }

    private fun initAdapter() {
        val verticalLayoutManager = GridLayoutManager(
            activity, 1,
            GridLayoutManager.VERTICAL, false
        )

        moviesAdapter.onMovieClickedListener = this
        moviesAdapter.onAddToFavoriteClickedListener = this

        _moviesBinding.rvMovies.layoutManager = verticalLayoutManager
        _moviesBinding.rvMovies.adapter = moviesAdapter
    }

    private fun observeViewModel() {
        collectLatestLifecycleFlow(moviesViewModel.loading) {
            when (it) {
                true -> _moviesBinding.loading.visibility = View.VISIBLE
                else -> _moviesBinding.loading.visibility = View.GONE
            }
        }

        collectLatestLifecycleFlow(moviesViewModel.error) {
            if (it) {
                Toast.makeText(requireContext(), "Error !!! , Sorry", Toast.LENGTH_LONG).show()
            }
        }

        collectLatestLifecycleFlow(moviesViewModel.movieData) {
            initAdapter()
            it?.let { list -> moviesAdapter.addItems(list) }
        }
    }

    override fun handleOnMovieClicked(movie: Movie) {
        DisplayMovieDialog(movie).show(childFragmentManager, "DisplayMovieDialog")
    }

    override fun handleAddToFavoriteClicked(movie: Movie) {
        val newMovie = movie.copy(isFavorite = true)
        moviesViewModel.insertMovie(movie)
    }
}