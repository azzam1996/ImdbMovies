package com.azzam.imdbmovies.domain.model

data class Movie(
    val id: String,
    val rank: String?,
    val rankUpDown: String?,
    val title: String?,
    val fullTitle: String?,
    val year: String?,
    val image: String?,
    val crew: String?,
    val imDbRating: String?,
    val imDbRatingCount: String?,
    var isFavorite: Boolean = false,
)