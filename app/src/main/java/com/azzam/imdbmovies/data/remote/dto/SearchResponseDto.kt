package com.azzam.imdbmovies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("results") val results: List<MovieDto>?,
    @SerializedName("expression") val expression: String?,
)
