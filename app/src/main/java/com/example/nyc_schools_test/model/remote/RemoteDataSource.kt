package com.example.nyc_schools_test.model.remote

import com.example.nyc_schools_test.common.FailedResponseException
import com.example.nyc_schools_test.common.NullResponseException
import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationResultResponse
import com.example.rappitest.model.remote.upcoming_response.ResultResponse
import com.example.rappitest.model.remote.upcoming_response.TopRatedResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RemoteDataSource {
    fun UpcomingCatched(): Flow<StateAction>
    fun TopRatedCatched(): Flow<StateAction>
    fun RecomendationCatched(): Flow<StateAction>
}

class RemoteDataSourceImpl @Inject constructor(
    private val service: RemoteApi
) : RemoteDataSource {

    override fun RecomendationCatched(): Flow<StateAction> = flow {
        val respose = service.getRecomendationList()
        if (respose.isSuccessful) {
            respose.body()?.let { result ->
                emit(
                    StateAction.SUCCESS(
                        result.results.toDomainRecomendationModel(),
                        ""
                    )
                )
            } ?: throw NullResponseException()
        } else {
            throw FailedResponseException()
        }

    }

    override fun UpcomingCatched(): Flow<StateAction> = flow {
        val respose = service.getUpcomingList()
        if (respose.isSuccessful) {
            respose.body()?.let { result ->
                emit(
                    StateAction.SUCCESS(
                        result.results.toDomainUpcomingModel(),
                        "Data From Network"
                    )
                )
            } ?: throw NullResponseException()
        } else {
            throw FailedResponseException()
        }

    }

    override fun TopRatedCatched(): Flow<StateAction> = flow {
        val respose = service.getTopRatedList()
        if (respose.isSuccessful) {
            respose.body()?.let { result ->
                emit(
                    StateAction.SUCCESS(
                        result.results.toDomainTopRatedModel(),
                        "Data From Network"
                    )
                )
            } ?: throw NullResponseException()
        } else {
            throw FailedResponseException()
        }

    }


}

private fun List<ResultResponse>.toDomainUpcomingModel(): List<UpcomingDomain> = map {
    it.toDomainUpcomingModel()
}

private fun List<TopRatedResultResponse>.toDomainTopRatedModel(): List<TopRatedDomain> = map {
    it.toDomainTopRatedModel()
}

private fun List<RecomendationResultResponse>.toDomainRecomendationModel(): List<RecomendationDomain> =
    map {
        it.toDomainRecomendationModel()
    }

private fun RecomendationResultResponse.toDomainRecomendationModel(): RecomendationDomain =
    RecomendationDomain(
        adult,
        backdropPath,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )

private fun TopRatedResultResponse.toDomainTopRatedModel(): TopRatedDomain =
    TopRatedDomain(
        adult,
        backdropPath,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )


private fun ResultResponse.toDomainUpcomingModel(): UpcomingDomain =
    UpcomingDomain(
        adult,
        backdropPath,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )


