package com.example.imdbsearch.domain.repository.searchmovies

import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.utils.DataResult

interface MoviesSearchRepository {

    suspend fun fetchMoviesListBySearchQuery(
        searchQuery: String,
        pageNumber: Int
    ) : DataResult<MoviesSearchResultResponse>

}