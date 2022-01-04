package com.example.imdbsearch.di

import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.domain.network.NetworkHelper
import com.example.imdbsearch.utils.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppMainModule {

    @Provides
    fun providesMoviesApiService() : MoviesApiService {
        return NetworkHelper
            .getRetrofit(NetworkConstants.OMDB_API_BASE_URL)
            .create(MoviesApiService::class.java)
    }

}