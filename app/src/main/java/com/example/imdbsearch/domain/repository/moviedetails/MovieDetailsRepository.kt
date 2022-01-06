package com.example.imdbsearch.domain.repository.moviedetails

import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.utils.DataResult

interface MovieDetailsRepository {

    suspend fun fetchMovieDetailsById(imdbId: String) : DataResult<MovieDetailsResponse>
}