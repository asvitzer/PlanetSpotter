package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetsRepository {

    fun getPlanetsFlow(): Flow<WorkResult<List<Planet>>>
    fun getPlanetFlow(planetId: String): Flow<WorkResult<Planet?>>
    suspend fun refreshPlanets()
    suspend fun addPlanet(planet: Planet)
    suspend fun deletePlanet(planetId: String)
}