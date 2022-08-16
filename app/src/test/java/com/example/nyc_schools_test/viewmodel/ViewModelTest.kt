package com.example.nyc_schools_test.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.RecomendationUseCase
import com.example.nyc_schools_test.domain.TopRatedUseCase
import com.example.nyc_schools_test.domain.UpcomingUseCase
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.assertEquals


@ExperimentalCoroutinesApi
class ViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()
    lateinit var handler: CoroutineExceptionHandler
    lateinit var subject: ViewModel
    lateinit var upcomingUseCase: UpcomingUseCase
    lateinit var topRatedUseCase: TopRatedUseCase
    lateinit var recomendationUseCase: RecomendationUseCase
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScopeCoroutine = TestScope(testDispatcher)

    @Before
    fun setUpTest() {
        upcomingUseCase = mockk()
        topRatedUseCase = mockk()
        recomendationUseCase = mockk()
        subject = ViewModel(
            handler,
            upcomingUseCase,
            topRatedUseCase,
            recomendationUseCase,
            testScopeCoroutine
        )
    }

    @Test
    fun `test everything works`() = runTest(testDispatcher) {
        testDispatcher.scheduler.advanceTimeBy(2000L)
        Assert.assertTrue(true)
    }

    @Test
    fun `get RECOMENDATION list when fetching data from server returns SUCCESS response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            val listRecomendation = listOf(mockk<RecomendationDomain>(relaxed = true))

            every {
                recomendationUseCase.invoke("ja")
            } returns flowOf(
                StateAction.SUCCESS(
                    listOf(listRecomendation)
                )
            )
            /**
             * When
             */
            subject.getRecomendationList("ja")
            var StateActionTestList = mutableListOf<StateAction>()

            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.recomendationResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<RecomendationDomain>>
            assertEquals(successTest.response.size, StateActionTestList.size)

            job.cancel()

        }

    @Test
    fun `get RECOMENDATION list when fetching data from server returns ERROR response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            every {
                recomendationUseCase.invoke("ja")
            } returns flowOf(
                StateAction.ERROR(FailedResponseException())
            )
            /**
             * When
             */
            subject.getRecomendationList("ja")
            var StateActionTestList = mutableListOf<StateAction>()
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.recomendationResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val errorTest =
                StateActionTestList.get(0) as StateAction.ERROR
            assertThat(errorTest).isInstanceOf(StateAction.ERROR::class.java)


            job.cancel()

        }

    @Test
    fun `get TOP RATED list when fetching data from server returns SUCCESS response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            val listTopRated = listOf(mockk<TopRatedDomain>(relaxed = true))

            every {
                topRatedUseCase.invoke()
            } returns flowOf(
                StateAction.SUCCESS(
                    listOf(listTopRated)
                )
            )
            /**
             * When
             */
            subject.getTopRatedList()
            var StateActionTestList = mutableListOf<StateAction>()
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.topRatedResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<TopRatedDomain>>
            assertEquals(successTest.response.size, StateActionTestList.size)

            job.cancel()

        }


    @Test
    fun `get TOP RATED list when fetching data from server returns ERROR response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            every {
                topRatedUseCase.invoke()
            } returns flowOf(
                StateAction.ERROR(FailedResponseException())
            )
            /**
             * When
             */
            subject.getTopRatedList()
            var StateActionTestList = mutableListOf<StateAction>()
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.topRatedResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val errorTest =
                StateActionTestList.get(0) as StateAction.ERROR
            assertThat(errorTest).isInstanceOf(StateAction.ERROR::class.java)

            job.cancel()

        }

    @Test
    fun `get UPCOMING list when fetching data from server returns SUCCESS response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            val listUpcoming = listOf(mockk<UpcomingDomain>(relaxed = true))

            every {
                upcomingUseCase.invoke()
            } returns flowOf(
                StateAction.SUCCESS(
                    listOf(listUpcoming)
                )
            )
            /**
             * When
             */
            subject.getUpcomingList()
            var StateActionTestList = mutableListOf<StateAction>()
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.upcomingResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val successTest =
                StateActionTestList.get(0) as StateAction.SUCCESS<List<UpcomingDomain>>
            assertEquals(successTest.response.size, StateActionTestList.size)

            job.cancel()

        }


    @Test
    fun `get UPCOMING list when fetching data from server returns ERROR response`() =
        runTest(testDispatcher) {
            /**
             * Given
             */
            every {
                upcomingUseCase.invoke()
            } returns flowOf(
                StateAction.ERROR(FailedResponseException())
            )
            /**
             * When
             */
            subject.getUpcomingList()
            var StateActionTestList = mutableListOf<StateAction>()
            val job = launch(handler) {
                supervisorScope {
                    launch {
                        subject.upcomingResponse.observeForever {
                            testDispatcher.scheduler.advanceTimeBy(1000L)
                            StateActionTestList.add(it)
                        }
                    }
                }
            }

            /**
             * Then
             */
            val errorTest =
                StateActionTestList.get(0) as StateAction.ERROR
            assertThat(errorTest).isInstanceOf(StateAction.ERROR::class.java)

            job.cancel()

        }


    @After
    fun shutdownTest() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

}