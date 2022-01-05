package com.example.imdbsearch.domain.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("Title") val title: String? = "",
    @SerializedName("Year") val year: String? = "",
    @SerializedName("Rated") val rated: String? = "",
    @SerializedName("Released") val releaseDate: String? = "",
    @SerializedName("Runtime") val runtime: String? = "",
    @SerializedName("Genre") val genre: String? = "",
    @SerializedName("Director") val director: String? = "",
    @SerializedName("Writer") val writer: String? = "",
    @SerializedName("Actors") val actors: String? = "",
    @SerializedName("Plot") val plot: String? = "",
    @SerializedName("Language") val language: String? = "",
    @SerializedName("Country") val country: String? = "",
    @SerializedName("Awards") val awards: String? = "",
    @SerializedName("Poster") val posterUrl: String? = "",
    @SerializedName("Type") val type: String? = "",
    val imdbRating: String? = "",
    @SerializedName("Ratings") val ratings: List<MovieRatingResponse>? = listOf()
) {
    val castAndCrew: String get() = allCrewMembers()

    private fun allCrewMembers(): String {
        var finalCrewList = ""
        listOf<String?>(director, actors, writer).forEach { person ->
            finalCrewList += returnValidEntryForString(person ?: "")
        }
        return finalCrewList
    }

    private fun returnValidEntryForString(person: String): String {
        return if (person.isNotEmpty() && !person.equals("N/A", true)) {
            "$person, "
        } else {
            ""
        }
    }
}

data class MovieRatingResponse(
    @SerializedName("Source") val source: String? = "",
    @SerializedName("Value") val value: String? = ""
)