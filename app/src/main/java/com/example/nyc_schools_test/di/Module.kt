package com.example.nyc_schools_test.di

import android.content.Context
import androidx.room.Room
import com.example.nyc_schools_test.common.BASE_URL
import com.example.nyc_schools_test.common.DATABASE_NAME
import com.example.nyc_schools_test.model.local.LocalDataSource
import com.example.nyc_schools_test.model.local.LocalDataSourceImpl
import com.example.nyc_schools_test.model.local.MoviesDB
import com.example.nyc_schools_test.model.remote.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideNycService(): RemoteApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(RemoteApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

}

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelProvidesModule {
    @Provides
    fun provideExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { context, throwable -> }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    fun provideCoroutineScope(dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher)


    @Provides
    @ProductionDB
    fun provideRoom(@ApplicationContext context: Context): MoviesDB =
        Room.databaseBuilder(
            context,
            MoviesDB::class.java, DATABASE_NAME
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideDao(@ProductionDB dataBase: MoviesDB) = dataBase.moviesDao()

}

@Module()
@InstallIn(ViewModelComponent::class)
abstract class ViewModelBindModule {

    @ViewModelScoped
    @Binds
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository

    @ViewModelScoped
    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @ViewModelScoped
    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource


}
