package com.example.imdbsearch.di

import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.domain.network.NetworkHelper
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepository
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepositoryImpl
import com.example.imdbsearch.utils.NetworkConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppMainModule {

    @Provides
    fun providesMoviesApiService() : MoviesApiService {
        return NetworkHelper
            .getRetrofit(NetworkConstants.OMDB_API_BASE_URL)
            .create(MoviesApiService::class.java)
    }

}
@Module
@InstallIn(ViewModelComponent::class)
interface MoviesSearchBindingModule {

    @Binds
    fun bindMoviesSearchRepository(repository: MoviesSearchRepositoryImpl) : MoviesSearchRepository

}