package com.example.imdbsearch.domain.repository.moviedetails

import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.domain.repository.BaseRepository
import com.example.imdbsearch.utils.DataResult
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val api: MoviesApiService
) : MovieDetailsRepository, BaseRepository() {

    override suspend fun fetchMovieDetailsById(imdbId: String): DataResult<MovieDetailsResponse> {
        return safeApiCall { api.fetchMovieDetailsById(imdbId) }
    }
}