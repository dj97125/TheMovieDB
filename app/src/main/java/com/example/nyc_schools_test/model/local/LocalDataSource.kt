package com.example.nyc_schools_test.model.local

import com.example.nyc_schools_test.common.StateAction
import com.example.nyc_schools_test.domain.responses.TopRatedDomain
import com.example.nyc_schools_test.domain.responses.UpcomingDomain
import com.example.nyc_schools_test.model.local.entities.TopRatedEntity
import com.example.rappitest.model.remote.upcoming_response.RecomendationDomain
import com.example.rappitest.model.remote.upcoming_response.RecomendationEntity
import com.example.rappitest.model.remote.upcoming_response.UpcomingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LocalDataSource {
    fun insertUpcoming(upcomingEntity: List<UpcomingDomain>): Flow<StateAction>
    fun insertTopRated(topRatedEntity: List<TopRatedDomain>): Flow<StateAction>
    fun insertRecomendation(recomendationEntity: List<RecomendationDomain>): Flow<StateAction>
    fun getAllRecomendation(): Flow<StateAction>
    fun getAllUpcoming(): Flow<StateAction>
    fun getAllTopRated(): Flow<StateAction>
    suspend fun deleteAllRecomendation()

}

class LocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : LocalDataSource {
    override fun insertUpcoming(upcomingEntity: List<UpcomingDomain>): Flow<StateAction> = flow {
        moviesDao.insertUpcoming(upcomingEntity.fromDomainUpcomingModel())
    }

    override fun insertTopRated(topRatedEntity: List<TopRatedDomain>): Flow<StateAction> = flow {
        moviesDao.insertTopRated(topRatedEntity.fromDomainTopRatedModel())
    }

    override fun insertRecomendation(recomendationEntity: List<RecomendationDomain>): Flow<StateAction> =
        flow {
            moviesDao.insertRecomendation(recomendationEntity.fromDomainRecomendationModel())
        }

    override fun getAllRecomendation(): Flow<StateAction> = flow {
        val cache = moviesDao.getAllRecomendation()
        cache.let {
            emit(StateAction.SUCCESS(cache.map { it.toDomainRecomendationModel() }, ""))
        }
    }

    override fun getAllUpcoming(): Flow<StateAction> = flow {
        val cache = moviesDao.getAllUpcoming()
        cache.let {
            emit(StateAction.SUCCESS(cache.map { it.toDomainUpcomingModel() }, "Data From Cache"))
        }
    }

    override fun getAllTopRated(): Flow<StateAction> = flow {
        val cache = moviesDao.getAllTopRated()
        cache.let {
            emit(StateAction.SUCCESS(cache.map { it.toDomainTopRatedModel() }, "Data From Cache"))
        }
    }

    override suspend fun deleteAllRecomendation() {
        return moviesDao.deleteAllRecomendation()
    }

}

private fun List<UpcomingEntity>.toDomainUpcomingModel(): List<UpcomingDomain> = map {
    it.toDomainUpcomingModel()
}

private fun List<TopRatedEntity>.toDomainTopRatedModel(): List<TopRatedDomain> = map {
    it.toDomainTopRatedModel()
}

private fun List<RecomendationEntity>.toDomainRecomendationModel(): List<RecomendationDomain> =
    map {
        it.toDomainRecomendationModel()
    }


private fun List<UpcomingDomain>.fromDomainUpcomingModel(): List<UpcomingEntity> = map {
    it.fromDomainUpcomingModel()
}

private fun List<TopRatedDomain>.fromDomainTopRatedModel(): List<TopRatedEntity> = map {
    it.fromDomainTopRatedModel()
}

private fun List<RecomendationDomain>.fromDomainRecomendationModel(): List<RecomendationEntity> =
    map {
        it.fromDomainRecomendationModel()
    }

private fun RecomendationEntity.toDomainRecomendationModel(): RecomendationDomain =
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

private fun RecomendationDomain.fromDomainRecomendationModel(): RecomendationEntity =
    RecomendationEntity(
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

private fun TopRatedEntity.toDomainTopRatedModel(): TopRatedDomain = TopRatedDomain(
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

private fun TopRatedDomain.fromDomainTopRatedModel(): TopRatedEntity = TopRatedEntity(
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

private fun UpcomingDomain.fromDomainUpcomingModel(): UpcomingEntity = UpcomingEntity(
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


private fun UpcomingEntity.toDomainUpcomingModel(): UpcomingDomain = UpcomingDomain(
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







