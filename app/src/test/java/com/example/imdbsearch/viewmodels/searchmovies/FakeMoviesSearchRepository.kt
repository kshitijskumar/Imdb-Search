package com.example.imdbsearch.viewmodels.searchmovies

import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.models.SingleMovieItem
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepository
import com.example.imdbsearch.utils.DataResult
import java.lang.NullPointerException

class FakeMoviesSearchRepository : MoviesSearchRepository {

    companion object {
        const val VALID_QUERY = "valid"
        const val VALID_QUERY_2 = "valid2"
        const val INVALID_QUERY = "invalid"
        private const val PAGE_2 = "page2"
    }

    private val fakeDb = mutableMapOf<String, MoviesSearchResultResponse>().apply {
        put(VALID_QUERY, MoviesSearchResultResponse(searchResultList = listOf(SingleMovieItem(title = "mov1"))))
        put(VALID_QUERY_2, MoviesSearchResultResponse(searchResultList = listOf(SingleMovieItem(title = "mov2"))))
        put(PAGE_2, MoviesSearchResultResponse(searchResultList = listOf(SingleMovieItem(title = "movie 3"), SingleMovieItem(title = "movie 4"))))
    }


    override suspend fun fetchMoviesListBySearchQuery(
        searchQuery: String,
        pageNumber: Int,
    ): DataResult<MoviesSearchResultResponse> {
        return fakeDb[searchQuery]?.let {
            if (pageNumber == 1) {
                DataResult.Success(it.apply { searchQueryForResult = searchQuery })
            } else {
                DataResult.Success(fakeDb[PAGE_2]!!.apply { searchQueryForResult = searchQuery })
            }
        } ?: DataResult.Error(NullPointerException(), "invalid query")
    }
}