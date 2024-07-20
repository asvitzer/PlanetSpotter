package com.asvitzer.planetspotters.di

import android.content.Context
import androidx.room.Room
import com.asvitzer.planetspotters.BuildConfig
import com.asvitzer.planetspotters.data.repo.DefaultGeminiRepository
import com.asvitzer.planetspotters.data.repo.DefaultPlanetsRepository
import com.asvitzer.planetspotters.data.repo.GeminiRepository
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.data.source.locale.LocalDataSource
import com.asvitzer.planetspotters.data.source.locale.PlanetsDatabase
import com.asvitzer.planetspotters.data.source.locale.RoomLocalDataSource
import com.asvitzer.planetspotters.data.source.remote.ApiRemoteDataSource
import com.asvitzer.planetspotters.data.source.remote.RemoteDataSource
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePlanetsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
    ): PlanetsRepository {
        return DefaultPlanetsRepository(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGeminiRepository(
        generativeModel: GenerativeModel
    ): GeminiRepository {
        return DefaultGeminiRepository(generativeModel)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return ApiRemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: PlanetsDatabase
    ): LocalDataSource {
        return RoomLocalDataSource(database.planetsDao())
    }

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PlanetsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PlanetsDatabase::class.java,
            "Planets.db"
        ).build()
    }
}