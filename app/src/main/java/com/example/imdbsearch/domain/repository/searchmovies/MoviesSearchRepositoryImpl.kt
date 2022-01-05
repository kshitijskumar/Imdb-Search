package com.example.imdbsearch.domain.repository.searchmovies

import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.domain.repository.BaseRepository
import com.example.imdbsearch.utils.DataResult
import javax.inject.Inject

class MoviesSearchRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApiService
) : MoviesSearchRepository, BaseRepository() {

    override suspend fun fetchMoviesListBySearchQuery(
        searchQuery: String,
        pageNumber: Int,
    ): DataResult<MoviesSearchResultResponse> {
        val dataResult = safeApiCall {
            moviesApi.fetchMoviesListForSearchQuery(
                searchQuery = searchQuery,
                pageNumber = pageNumber.toString()
            )
        }
        return when (dataResult) {
            is DataResult.Success -> {
                dataResult.data.apply { searchQueryForResult = searchQuery }
                dataResult
            }
            else -> dataResult
        }
    }
}