package com.example.imdbsearch.di

import com.example.imdbsearch.BuildConfig
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.domain.network.NetworkHelper
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepository
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppMainModule {

    @Provides
    fun providesMoviesApiService() : MoviesApiService {
        return NetworkHelper
            .getRetrofit(BuildConfig.OMDB_BASE_URL)
            .create(MoviesApiService::class.java)
    }

}
@Module
@InstallIn(ViewModelComponent::class)
interface MoviesSearchBindingModule {

    @Binds
    fun bindMoviesSearchRepository(repository: MoviesSearchRepositoryImpl) : MoviesSearchRepository

}