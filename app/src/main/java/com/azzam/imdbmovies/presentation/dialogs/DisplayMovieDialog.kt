package com.azzam.imdbmovies.presentation.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import com.azzam.imdbmovies.R
import com.azzam.imdbmovies.databinding.LayoutDisplayMovieBinding
import com.azzam.imdbmovies.domain.model.Movie
import com.azzam.imdbmovies.domain.util.Dimension.pxFromDp
import com.azzam.imdbmovies.domain.util.loadImage


class DisplayMovieDialog(var movie: Movie) : DialogFragment() {

    private lateinit var _binding: LayoutDisplayMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.layout_display_movie, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, pxFromDp(requireContext(), 500f).toInt())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LayoutDisplayMovieBinding.bind(view)

        _binding.ivCancel.setOnClickListener { dismiss() }

        _binding.tvMovieTitle.text = movie.fullTitle
        _binding.tvCrew.text = movie.crew
        _binding.tvRating.text = movie.imDbRating
        _binding.ivImage.loadImage(movie.image, _binding.progressBar)
    }

}