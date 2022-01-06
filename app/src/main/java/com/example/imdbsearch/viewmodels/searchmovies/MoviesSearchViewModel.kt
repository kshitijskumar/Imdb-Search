package com.example.imdbsearch.viewmodels.searchmovies

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.models.SingleMovieItem
import com.example.imdbsearch.domain.repository.searchmovies.MoviesSearchRepository
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
            handleSearchResultResponse(searchResult)
        }
    }

    private fun handleSearchResultResponse(searchResult: DataResult<MoviesSearchResultResponse>) {
        when (searchResult) {
            is DataResult.Success -> {
                if (currentPageNumber == 1) {
                    // some new data arrived
                    _searchResultState.postValue(searchResult)
                } else {
                    // new data due to pagination so completeList = old + new
                    if (searchResultState.value is DataResult.Success) {
                        val currentMoviesList = (searchResultState.value as DataResult.Success).data.searchResultList ?: listOf()
                        val newMoviesList = searchResult.data.searchResultList ?: listOf()

                        val updatedMoviesList = mutableListOf<SingleMovieItem>().apply {
                            addAll(currentMoviesList)
                            addAll(newMoviesList)
                        }
                        val updatedResultData = searchResult.data.copy(searchResultList = updatedMoviesList).apply {
                            searchQueryForResult = searchResult.data.searchQueryForResult
                        }
                        _searchResultState.postValue(
                            DataResult.Success(updatedResultData)
                        )
                    } else {
                        _searchResultState.postValue(searchResult)
                    }

                }
            }
            else -> _searchResultState.postValue(searchResult)
        }
    }

    fun updateCurrentSearchQuery(query: String) {
        currentSearchQuery = query
        currentPageNumber = 1
        if (query != "") {
            initiateSearchForQuery()
            _searchResultState.value = DataResult.Loading
        } else {
            searchingJob?.cancel()
            _searchResultState.value = DataResult.Idle
        }
    }

    fun updateCurrentSearchQuery(editable: Editable?) {
        val query = editable?.toString() ?: return
        updateCurrentSearchQuery(query)
    }

    fun updateCurrentPageNumber() {
        currentPageNumber += 1
        initiateSearchForQuery()
    }

}