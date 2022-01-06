package com.example.imdbsearch.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

//@ExperimentalCoroutinesApi
//class MainCoroutineRule : TestWatcher() {
//
//    override fun starting(description: Description?) {
//        super.starting(description)
//        Dispatchers.setMain(Dispatchers.Unconfined)
//    }
//
//    override fun finished(description: Description?) {
//        super.finished(description)
//        Dispatchers.resetMain()
//    }
//}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    suspend fun bringSomeDelay() {
        testDispatcher.pauseDispatcher()
        delay(2000)
        testDispatcher.resumeDispatcher()
    }
}