package com.example.nyc_schools_test.model.remote

import android.util.Log
import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.InternetCheck
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.nyc_schools_test.model.local.LocalDataSource
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface Repository {
    fun UpcomingCatched(): Flow<StateAction>
    fun TopRatedCatched(): Flow<StateAction>
    fun RecomendationCatched(response: String): Flow<StateAction>
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override fun UpcomingCatched(): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.UpcomingCatched()
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedUpcoming = stateAction.response as List<UpcomingDomain>
                        emit(StateAction.SUCCESS(retrievedUpcoming))
                        localDataSource.insertUpcoming(retrievedUpcoming).collect()

                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }

            }
        } else {
            val cache = localDataSource.getAllUpcoming()
            cache.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedUpcoming = stateAction.response as List<UpcomingDomain>
                        emit(StateAction.SUCCESS(retrievedUpcoming))
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }
            }
        }
    }

    override fun TopRatedCatched(): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.TopRatedCatched()
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedTopRated = stateAction.response as List<TopRatedDomain>
                        emit(StateAction.SUCCESS(retrievedTopRated))
                        localDataSource.insertTopRated(retrievedTopRated).collect()

                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }
            }
        } else {
            val cache = localDataSource.getAllTopRated()
            cache.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedTopRated = stateAction.response as List<TopRatedDomain>
                        emit(StateAction.SUCCESS(retrievedTopRated))
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }
            }
        }
    }

    override fun RecomendationCatched(response: String): Flow<StateAction> = flow {
        val connected = InternetCheck()
        val remoteService = remoteDataSource.RecomendationCatched()
        if (connected.isConnected()) {
            remoteService.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedRecomendation =
                            stateAction.response as List<RecomendationDomain>
                        localDataSource.insertRecomendation(retrievedRecomendation).collect()
                        if (response == "ja") {
                            emit(StateAction.SUCCESS(retrievedRecomendation.filter { x ->
                                x.originalLanguage == response
                            }))
                        }
                        if (response == "2022") {
                            emit(StateAction.SUCCESS(retrievedRecomendation.filter { x ->
                                x.releaseDate.take(4) == response
                            }))
                        }
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }
            }
        } else {
            val cache = localDataSource.getAllRecomendation()
            cache.collect { stateAction ->
                when (stateAction) {
                    is StateAction.SUCCESS<*> -> {
                        val retrievedRecomendation =
                            stateAction.response as List<RecomendationDomain>
                        if (response == "ja") {
                            emit(StateAction.SUCCESS(retrievedRecomendation.filter { x ->
                                x.originalLanguage == response
                            }))
                        }
                        if (response == "2022") {
                            emit(StateAction.SUCCESS(retrievedRecomendation.filter { x ->
                                x.releaseDate.take(4) == response
                            }))
                        }
                    }
                    is StateAction.ERROR -> {
                        emit(StateAction.ERROR(FailedResponseException()))
                    }
                }
            }
        }
    }
}



