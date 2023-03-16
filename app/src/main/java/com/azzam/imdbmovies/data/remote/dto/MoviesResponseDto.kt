package com.azzam.imdbmovies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("items") val items: List<MovieDto>?,
    @SerializedName("errorMessage") val errorMessage: String?,
)
