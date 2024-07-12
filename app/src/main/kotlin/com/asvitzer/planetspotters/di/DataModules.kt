package com.asvitzer.planetspotters.di

import android.content.Context
import androidx.room.Room
import com.asvitzer.planetspotters.data.repo.DefaultPlanetsRepository
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.data.source.locale.LocalDataSource
import com.asvitzer.planetspotters.data.source.locale.PlanetsDatabase
import com.asvitzer.planetspotters.data.source.locale.RoomLocalDataSource
import com.asvitzer.planetspotters.data.source.remote.ApiRemoteDataSource
import com.asvitzer.planetspotters.data.source.remote.RemoteDataSource
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddPlanetUseCase(
        repository: PlanetsRepository
    ): AddPlanetUseCase {
        return AddPlanetUseCase(repository)
    }
}

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
