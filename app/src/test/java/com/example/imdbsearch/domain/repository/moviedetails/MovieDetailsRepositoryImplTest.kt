package com.example.imdbsearch.domain.repository.moviedetails

import com.example.imdbsearch.domain.models.MovieDetailsResponse
import com.example.imdbsearch.domain.network.MoviesApiService
import com.example.imdbsearch.utils.DataResult
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
class MovieDetailsRepositoryImplTest {

    companion object {
        private const val VALID_IMDB_ID = "VALID_IMDB_ID"
        private const val INVALID_IMDB_ID = "INVALID_IMDB_ID"
    }

    @Mock
    lateinit var apiService: MoviesApiService

    private lateinit var subjectUnderTest: MovieDetailsRepositoryImpl

    @Before
    fun setup() {
        subjectUnderTest = MovieDetailsRepositoryImpl(apiService)
    }

    @Test
    fun fetchMovieDetailsById_onApiSuccess_returnsDataResultSuccess(): Unit = runTest {
        val expectedApiResponse = Response.success(MovieDetailsResponse())
        Mockito.`when`(apiService.fetchMovieDetailsById(VALID_IMDB_ID)).thenReturn(expectedApiResponse)

        val result = subjectUnderTest.fetchMovieDetailsById(VALID_IMDB_ID)

        assertThat(result, instanceOf(DataResult.Success::class.java))
    }

    @Test
    fun fetchMovieDetailsById_onNullDataInApiSuccess_returnsDataResultError(): Unit = runTest {
        val expectedApiResponse = Response.success<MovieDetailsResponse>(null)
        Mockito.`when`(apiService.fetchMovieDetailsById(INVALID_IMDB_ID)).thenReturn(expectedApiResponse)

        val result = subjectUnderTest.fetchMovieDetailsById(INVALID_IMDB_ID)

        assertThat(result, instanceOf(DataResult.Error::class.java))
    }

    @Test
    fun fetchMovieDetailsById_onApiError_returnsDataResultError(): Unit = runTest {
        val dummyApiResponseBody = ResponseBody.create(MediaType.parse(""), "")
        val expectedApiResponse = Response.error<MovieDetailsResponse>(404, dummyApiResponseBody)
        Mockito.`when`(apiService.fetchMovieDetailsById(VALID_IMDB_ID)).thenReturn(expectedApiResponse)

        val result = subjectUnderTest.fetchMovieDetailsById(VALID_IMDB_ID)

        assertThat(result, instanceOf(DataResult.Error::class.java))
    }

}