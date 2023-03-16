package com.azzam.imdbmovies.data.remote

import android.content.Context
import com.azzam.imdbmovies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun create(context: Context): Api {
        val okHttpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient.addInterceptor(ConnectivityInterceptor(context))
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.readTimeout(25, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(25, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}
