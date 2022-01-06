package com.example.imdbsearch.viewmodels.moviedetails

import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.domain.repository.moviedetails.MovieDetailsRepository
import com.example.imdbsearch.utils.DataResult
import java.lang.NullPointerException

class FakeMovieDetailsRepository : MovieDetailsRepository {

    companion object {
        const val MOVIE_1 = "movie_1"
        const val MOVIE_2 = "movie_2"
        const val WRONG_ID = "wrong_id"
    }

    private val movieDetailsFakeDb = mutableMapOf<String, MovieDetailsResponse>().apply {
        put(MOVIE_1, MovieDetailsResponse(title = MOVIE_1))
        put(MOVIE_2, MovieDetailsResponse(title = MOVIE_2))
    }

    override suspend fun fetchMovieDetailsById(imdbId: String): DataResult<MovieDetailsResponse> {
        val movieDetails = movieDetailsFakeDb[imdbId]
        return movieDetails?.let {
            DataResult.Success(it)
        } ?: DataResult.Error(NullPointerException(), "no movie")
    }
}