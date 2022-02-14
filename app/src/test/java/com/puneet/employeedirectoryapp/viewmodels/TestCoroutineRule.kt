package com.puneet.employeedirectoryapp.viewmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/*
This class enables the main dispatcher to use TestCoroutineDispatcher during a unit test.
After the test, it resets and cleanup.
 */

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)
                try {
                    base?.evaluate()
                } finally {
                    Dispatchers.resetMain()
                    testCoroutineScope.cleanupTestCoroutines()
                }
            }
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}