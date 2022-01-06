package com.example.imdbsearch.viewmodels.searchmovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.utils.MainCoroutineRule
import com.example.imdbsearch.utils.getOrAwaitValue
import com.example.imdbsearch.viewmodels.searchmovies.FakeMoviesSearchRepository.Companion.INVALID_QUERY
import com.example.imdbsearch.viewmodels.searchmovies.FakeMoviesSearchRepository.Companion.VALID_QUERY
import com.example.imdbsearch.viewmodels.searchmovies.FakeMoviesSearchRepository.Companion.VALID_QUERY_2
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class MoviesSearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var subjectUnderTest: MoviesSearchViewModel

    @Before
    fun setup() {
        val fakeMoviesSearchRepository = FakeMoviesSearchRepository()
        subjectUnderTest = MoviesSearchViewModel(fakeMoviesSearchRepository)
    }

    @Test
    fun updateCurrentSearchQuery_onNonEmptyValidQueryString_emitsAnythingExceptDataResultIdle() : Unit = runTest {
        subjectUnderTest.updateCurrentSearchQuery(VALID_QUERY)

        val searchResultStateValue = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2)

        assertNotSame(DataResult.Idle, searchResultStateValue)
    }

    @Test
    fun updateCurrentSearchQuery_onNonEmptyInvalidQueryString_emitsAnythingExceptDataResultIdle() : Unit = runTest {
        subjectUnderTest.updateCurrentSearchQuery(INVALID_QUERY)

        mainCoroutineRule.bringSomeDelay()
        val searchResultStateValue = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2)

        assertNotSame(DataResult.Idle, searchResultStateValue)
    }

    @Test
    fun updateCurrentSearchQuery_onEmptyQueryString_emitsDataResultIdle() : Unit = runTest {
        subjectUnderTest.updateCurrentSearchQuery("")

        val searchResultStateValue = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2)

        assertSame(DataResult.Idle, searchResultStateValue)
    }

    @Test
    fun handleSearchResultResponse_forNewQueryString_replacesPreviousDataWithNewData() : Unit = runTest {
        subjectUnderTest.updateCurrentSearchQuery(VALID_QUERY)
        mainCoroutineRule.bringSomeDelay()
        val searchStateValueForPreviousQuery = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2) as DataResult.Success

        subjectUnderTest.updateCurrentSearchQuery(VALID_QUERY_2)
        mainCoroutineRule.bringSomeDelay()
        val searchStateValueForCurrentQuery = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2) as DataResult.Success

        assertThat(
            searchStateValueForPreviousQuery.data,
            not(equalTo(searchStateValueForCurrentQuery.data))
        )
    }

    @Test
    fun handleSearchResultResponse_forNextPageOfSameQuery_combinesPreviousAndNewList() : Unit = runTest {
        subjectUnderTest.updateCurrentSearchQuery(VALID_QUERY)
        mainCoroutineRule.bringSomeDelay()
        val searchStateValueForPreviousQuery = subjectUnderTest.searchResultState.getOrAwaitValue(time = 2) as DataResult.Success

        subjectUnderTest.updateCurrentPageNumber()
        mainCoroutineRule.bringSomeDelay()
        val searchStateValueForNextPage = subjectUnderTest.searchResultState.getOrAwaitValue(2) as DataResult.Success

        (0 until (searchStateValueForPreviousQuery.data.searchResultList?.size ?: 0)).forEach {
            assertEquals(
                searchStateValueForPreviousQuery.data.searchResultList?.get(it),
                searchStateValueForNextPage.data.searchResultList?.get(it)
            )
        }
    }


}