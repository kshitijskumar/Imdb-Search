package com.example.imdbsearch.domain.repository

import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.utils.UnsuccessfulResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.NullPointerException

abstract class BaseRepository {

    suspend fun <T>safeApiCall(apiCall: suspend () -> Response<T>) : DataResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        DataResult.Success(response.body()!!)
                    } else {
                        DataResult.Error(NullPointerException(), "Couldn't fetch the correct data")
                    }
                } else {
                    DataResult.Error(UnsuccessfulResponseException(), "Something went wrong on the server")
                }
            }catch (e: Exception) {
                DataResult.Error(e, "Something went wrong")
            }
        }
    }
}