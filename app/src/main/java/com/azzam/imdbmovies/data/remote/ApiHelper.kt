package com.azzam.imdbmovies.data.remote

import android.util.Log
import retrofit2.Response
import java.lang.Exception
import kotlinx.coroutines.CancellationException
import com.azzam.imdbmovies.domain.util.Result
import timber.log.Timber


private fun <T> parseResponse(response: Response<T>): Result<T?> {
    return if (response.isSuccessful) {
        Result.Success(response.body())
    } else {
        val errorMessage = response.errorBody()?.string()
        Result.Error(
            code = response.code(),
            errorBody = errorMessage,
        )
    }
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T?> {
    return try {
        parseResponse(apiCall.invoke())
    } catch (e: Exception) {
        Timber.tag("Imdb Movies : ").e(Log.getStackTraceString(e))
        when (e) {
            !is CancellationException -> {}
        }
        Result.Error(errorBody = e.message)
    }
}