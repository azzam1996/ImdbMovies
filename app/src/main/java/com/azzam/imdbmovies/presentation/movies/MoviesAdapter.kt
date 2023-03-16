package com.azzam.imdbmovies.presentation.movies


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azzam.imdbmovies.R
import com.azzam.imdbmovies.databinding.ItemMovieBinding
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.util.loadImage


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    var items = ArrayList<Movie>()


    interface OnMovieClickedListener {
        fun handleOnMovieClicked(movie: Movie)
    }

    var onMovieClickedListener: OnMovieClickedListener? = null

    interface OnAddToFavoriteClickedListener {
        fun handleAddToFavoriteClicked(movie: Movie)
    }

    var onAddToFavoriteClickedListener: OnAddToFavoriteClickedListener? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, null)
        )
    }


    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(list: List<Movie>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    inner class MovieViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(interfaceImplementation: Movie, position: Int) = with(view) {
            val movie = interfaceImplementation as Movie
            val binding = ItemMovieBinding.bind(view)


            binding.ivPoster.loadImage(movie.image, binding.progressBar)
            binding.tvTitle.text = movie.title
            binding.tvRating.text = movie.imDbRating

            if (movie.isFavorite)
                binding.ivFavorite.setImageResource(android.R.drawable.star_big_on)
            else
                binding.ivFavorite.setImageResource(android.R.drawable.star_off)

            setOnClickListener { onMovieClickedListener?.handleOnMovieClicked(movie) }
            binding.ivFavorite.setOnClickListener {
                onAddToFavoriteClickedListener?.handleAddToFavoriteClicked(movie)
            }
        }
    }
}