package com.azzam.imdbmovies.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.azzam.imdbmovies.R
import com.azzam.imdbmovies.databinding.FragmentMoviesBinding
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.util.collectLatestLifecycleFlow
import com.azzam.imdbmovies.presentation.dialogs.DisplayMovieDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.OnMovieClickedListener,
    MoviesAdapter.OnAddToFavoriteClickedListener {

    private val moviesViewModel by viewModel<MoviesViewModel>()

    private lateinit var _moviesBinding: FragmentMoviesBinding

    private val moviesAdapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _moviesBinding = FragmentMoviesBinding.inflate(inflater)
        return _moviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        collectViewModelData()
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

    private fun collectViewModelData() {
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
            it?.let { list -> moviesAdapter.addItems(list) }
        }
    }

    override fun handleOnMovieClicked(movie: Movie) {
        DisplayMovieDialog(movie).show(childFragmentManager, "DisplayMovieDialog")
    }

    override fun handleAddToFavoriteClicked(movie: Movie) {
        val newMovie = movie.copy(isFavorite = true)
        moviesViewModel.insertMovie(newMovie)
    }
}