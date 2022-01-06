package com.example.imdbsearch.viewmodels.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.utils.MainCoroutineRule
import com.example.imdbsearch.utils.getOrAwaitValue
import com.example.imdbsearch.viewmodels.moviedetails.FakeMovieDetailsRepository.Companion.MOVIE_1
import com.example.imdbsearch.viewmodels.moviedetails.FakeMovieDetailsRepository.Companion.WRONG_ID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var subjectUnderTest: MovieDetailsViewModel

    @Before
    fun setup() {
        val fakeRepo = FakeMovieDetailsRepository()
        subjectUnderTest = MovieDetailsViewModel(fakeRepo)
    }

    @Test
    fun fetchMovieDetailsById_onDataResultSuccess_emitsDataResultSuccess(): Unit = runTest {
        subjectUnderTest.fetchMovieDetailsById(MOVIE_1)

        val stateValue = subjectUnderTest.movieDetailsState.getOrAwaitValue(time = 1)

        assertThat(stateValue, instanceOf(DataResult.Success::class.java))

    }

    @Test
    fun fetchMovieDetailsById_onDataResultError_emitsDataResultError(): Unit = runTest {
        subjectUnderTest.fetchMovieDetailsById(WRONG_ID)

        val stateValue = subjectUnderTest.movieDetailsState.getOrAwaitValue(time = 1)

        assertThat(stateValue, instanceOf(DataResult.Error::class.java))

    }

    @Test
    fun subject_onInit_emitsDataResultLoading(): Unit = runTest {
        val stateValue = subjectUnderTest.movieDetailsState.getOrAwaitValue(time = 1)

        assertThat(stateValue, instanceOf(DataResult.Loading::class.java))

    }


}