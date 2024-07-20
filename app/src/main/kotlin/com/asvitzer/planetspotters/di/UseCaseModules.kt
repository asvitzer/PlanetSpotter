package com.asvitzer.planetspotters.di

import com.asvitzer.planetspotters.data.repo.GeminiRepository
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import com.asvitzer.planetspotters.domain.GeneratePlanetDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    @Singleton
    @Provides
    fun provideGeneratePlanetDataUseCase(
        geminiRepository: GeminiRepository
    ): GeneratePlanetDataUseCase {
        return GeneratePlanetDataUseCase(geminiRepository)
    }
}

