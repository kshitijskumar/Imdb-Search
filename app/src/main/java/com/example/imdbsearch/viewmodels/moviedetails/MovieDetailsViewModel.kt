package com.example.imdbsearch.viewmodels.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.domain.repository.moviedetails.MovieDetailsRepository
import com.example.imdbsearch.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MovieDetailsRepository
) : ViewModel() {

    private val _movieDetailsState = MutableLiveData<DataResult<MovieDetailsResponse>>(DataResult.Loading)
    val movieDetailsState: LiveData<DataResult<MovieDetailsResponse>> get() = _movieDetailsState

    fun fetchMovieDetailsById(imdbID: String) = viewModelScope.launch {
        val movieDetailsResult = repo.fetchMovieDetailsById(imdbID)
        _movieDetailsState.postValue(movieDetailsResult)

    }

}