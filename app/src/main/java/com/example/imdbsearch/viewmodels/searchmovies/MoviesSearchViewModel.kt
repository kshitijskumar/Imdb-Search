package com.example.imdbsearch.viewmodels.searchmovies

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepository
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepositoryImpl
import com.example.imdbsearch.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesSearchViewModel @Inject constructor(
    private val repo: MoviesSearchRepository
) : ViewModel() {

    private var currentSearchQuery: String = ""
    private var currentPageNumber: Int = 1

    private var searchingJob: Job? = null

    private val _searchResultState = MutableLiveData<DataResult<MoviesSearchResultResponse>>()
    val searchResultState: LiveData<DataResult<MoviesSearchResultResponse>> get() = _searchResultState

    private fun initiateSearchForQuery() {
        searchingJob?.cancel()
        searchingJob = viewModelScope.launch {
            delay(500) //adding some delay to let user type freely, rather than searching for every character
            val searchResult = repo.fetchMoviesListBySearchQuery(
                currentSearchQuery,
                currentPageNumber
            )
            _searchResultState.postValue(searchResult)
        }
    }

    fun updateCurrentSearchQuery(query: String) {
        currentSearchQuery = query
        currentPageNumber = 1
        initiateSearchForQuery()
    }

    fun updateCurrentSearchQuery(editable: Editable?) {
        val query = editable?.toString() ?: return
        currentSearchQuery = query
        currentPageNumber = 1
        if (query != "") {
            initiateSearchForQuery()
        }
    }

    fun updateCurrentPageNumber() {
        currentPageNumber += 1
        initiateSearchForQuery()
    }

}