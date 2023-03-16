package com.azzam.imdbmovies

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.azzam.imdbmovies.di.networkModule
import com.azzam.imdbmovies.di.repositoryModule
import com.azzam.imdbmovies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

class ImdbApp : Application() {

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate()

        startKoin {
            androidContext(this@ImdbApp)
            val modulesList = arrayListOf<Module>()
            modulesList.addAll(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    networkModule
                )
            )
            modules(modulesList)
        }

        initTimber()
    }


    private fun initTimber() {
        when (BuildConfig.DEBUG) {
            true -> Timber.plant(Timber.DebugTree())
            else -> {}
        }
    }
}