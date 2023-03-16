package com.azzam.imdbmovies.data.remote


import com.azzam.imdbmovies.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    @GET("MostPopularMovies/k_tue0gur8")
    suspend fun getMovies(): Response<MoviesResponseDto?>

    @GET("Search/k_tue0gur8/{expression}")
    suspend fun searchForMovie(@Path("expression") expression: String): Response<SearchResponseDto?>
}