package com.example.imdbsearch.utils

sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()
    object Loading : DataResult<Nothing>()
    data class Error(val e: Exception, val errorMsg: String) : DataResult<Nothing>()
    object Idle : DataResult<Nothing>()

}
