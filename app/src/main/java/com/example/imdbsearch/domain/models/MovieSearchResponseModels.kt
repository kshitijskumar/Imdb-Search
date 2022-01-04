package com.example.imdbsearch.domain.models

import com.google.gson.annotations.SerializedName

data class MoviesSearchResultResponse(
    @SerializedName("Search") val searchResultList: List<SingleMovieItem>? = listOf(),
    val totalResults: String = "",
    @SerializedName("Response") val response: String = ""
)

data class SingleMovieItem(
    @SerializedName("Title") val title: String? = "",
    @SerializedName("Year") val year: String? = "",
    @SerializedName("imdbID") val imdbId: String? = "",
    @SerializedName("Type") val type: String? = "",
    @SerializedName("Poster") val poster: String? = ""
)