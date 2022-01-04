package com.example.imdbsearch.domain.network

import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    companion object {
        private const val API_KEY_PARAM = "?apikey=${NetworkConstants.OMDB_API_KEY}"
    }

    @GET(API_KEY_PARAM)
    suspend fun fetchMoviesListForSearchQuery(
        @Query("s") searchQuery: String,
        @Query("page") pageNumber: String
    ) : Response<MoviesSearchResultResponse>

    @GET(API_KEY_PARAM)
    suspend fun fetchMovieDetailsById(
        @Query("i") imdbMovieId: String,
    ) : Response<MovieDetailsResponse>

}