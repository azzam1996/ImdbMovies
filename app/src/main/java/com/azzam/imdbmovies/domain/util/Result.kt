package com.azzam.imdbmovies.domain.util

sealed class Result<T>(val value: T?, val code: Int? = null, val errorBody: String? = null) {
    class Success<T>(value: T?) : Result<T>(value)
    class Error<T>(
        code: Int? = null,
        errorBody: String? = null,
    ) : Result<T>(null, code, errorBody)
}

