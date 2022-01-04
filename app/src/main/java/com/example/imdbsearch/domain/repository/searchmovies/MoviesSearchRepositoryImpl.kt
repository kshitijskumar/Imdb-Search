package com.example.imdbsearch.domain.repository.searchmovies

import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.utils.UnsuccessfulResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesSearchRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApiService
) : MoviesSearchRepository{

    override suspend fun fetchMoviesListBySearchQuery(
        searchQuery: String,
        pageNumber: Int,
    ): DataResult<MoviesSearchResultResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = moviesApi.fetchMoviesListForSearchQuery(searchQuery, pageNumber.toString())
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val searchResult = response.body()!!.apply {
                            searchQueryForResult = searchQuery
                        }
                        DataResult.Success(searchResult)
                    } else {
                        DataResult.Error(NullPointerException(), "No data found")
                    }
                } else {
                    DataResult.Error(UnsuccessfulResponseException(), "Something went wrong")
                }
            } catch (e: Exception) {
                DataResult.Error(e, "Something went wrong")
            }
        }
    }
}