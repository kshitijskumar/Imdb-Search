package com.example.imdbsearch.domain.repository.searchmovies

import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.utils.DataResult
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesSearchRepositoryImplTest {

    companion object {
        private const val SEARCH_QUERY = "some movie"
        private const val PAGE_NUMBER = 1
    }

    @Mock
    lateinit var apiService: MoviesApiService

    private lateinit var subjectUnderTest: MoviesSearchRepositoryImpl

    @Before
    fun setup() {
        subjectUnderTest = MoviesSearchRepositoryImpl(apiService)
    }

    @Test
    fun fetchMoviesListBySearchQuery_onApiSuccess_returnsDataResultSuccess(): Unit = runTest {
        val expectedResponse = Response.success(MoviesSearchResultResponse())
        Mockito.`when`(apiService.fetchMoviesListForSearchQuery(SEARCH_QUERY, PAGE_NUMBER.toString()))
            .thenReturn(expectedResponse)

        val result = subjectUnderTest.fetchMoviesListBySearchQuery(SEARCH_QUERY, PAGE_NUMBER)

        assertThat(result, instanceOf(DataResult.Success::class.java))
    }

    @Test
    fun fetchMoviesListBySearchQuery_onApiError_returnsDataResultError(): Unit = runTest {
        val dummyApiResponseBody = ResponseBody.create(MediaType.parse(""), "")
        val expectedResponse = Response.error<MoviesSearchResultResponse>(404, dummyApiResponseBody)
        Mockito.`when`(apiService.fetchMoviesListForSearchQuery(SEARCH_QUERY, PAGE_NUMBER.toString()))
            .thenReturn(expectedResponse)

        val result = subjectUnderTest.fetchMoviesListBySearchQuery(SEARCH_QUERY, PAGE_NUMBER)

        assertThat(result, instanceOf(DataResult.Error::class.java))
    }

    @Test
    fun fetchMoviesListBySearchQuery_onApiSuccess_returnsSearchQuerySetInApiResponse(): Unit = runTest {
        val expectedResponse = Response.success<MoviesSearchResultResponse>(MoviesSearchResultResponse())
        Mockito.`when`(apiService.fetchMoviesListForSearchQuery(SEARCH_QUERY, PAGE_NUMBER.toString()))
            .thenReturn(expectedResponse)

        val result = subjectUnderTest.fetchMoviesListBySearchQuery(SEARCH_QUERY, PAGE_NUMBER)

        assertThat(result, instanceOf(DataResult.Success::class.java))
        assertEquals((result as DataResult.Success).data.searchQueryForResult, SEARCH_QUERY)
    }

}