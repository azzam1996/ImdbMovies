package com.azzam.imdbmovies.di

import androidx.room.Room
import com.azzam.imdbmovies.data.local.MoviesDataBase
import com.azzam.imdbmovies.data.remote.RetrofitFactory
import com.azzam.imdbmovies.data.repository.MoviesRepositoryImpl
import com.azzam.imdbmovies.domain.repository.MoviesRepository
import com.azzam.imdbmovies.presentation.favourite.FavouriteViewModel
import com.azzam.imdbmovies.presentation.movies.MoviesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val repositoryModule = module {
    single { Room.databaseBuilder(androidContext(), MoviesDataBase::class.java, "movies_db").build().getRunDao() }
    single { MoviesRepositoryImpl(get(), get()) as MoviesRepository }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
}

val networkModule = module {
    single { RetrofitFactory.create(get()) }
}