package com.example.nyc_schools_test.model.local

import androidx.room.*
import com.example.nyc_schools_test.model.local.entities.TopRatedEntity
import com.example.rappitest.model.remote.upcoming_response.RecomendationEntity
import com.example.rappitest.model.remote.upcoming_response.UpcomingEntity


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcoming(upcomingEntity: List<UpcomingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRated(topRatedEntity: List<TopRatedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecomendation(recomendationEntity: List<RecomendationEntity>)

    @Query("SELECT * FROM UpcomingEntity order by id")
    suspend fun getAllUpcoming(): List<UpcomingEntity>

    @Query("SELECT * FROM TopRatedEntity order by id")
    suspend fun getAllTopRated(): List<TopRatedEntity>

    @Query("SELECT * FROM RecomendationEntity order by id")
    suspend fun getAllRecomendation(): List<RecomendationEntity>

    @Query("DELETE FROM RecomendationEntity")
    suspend fun deleteAllRecomendation()


}


@Database(
    version = 1,
    entities = [UpcomingEntity::class, TopRatedEntity::class, RecomendationEntity::class],
    exportSchema = false
)
abstract class MoviesDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}