package com.azzam.imdbmovies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: String,
    @SerializedName("rank") val rank: String?,
    @SerializedName("rankUpDown") val rankUpDown: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("fullTitle") val fullTitle: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("crew") val crew: String?,
    @SerializedName("imDbRating") val imDbRating: String?,
    @SerializedName("imDbRatingCount") val imDbRatingCount: String?,
)