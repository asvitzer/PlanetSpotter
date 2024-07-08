package com.asvitzer.planetspotters.data.source

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPlanetsFlow(): Flow<WorkResult<List<Planet>>>
    fun getPlanetFlow(planetId: String): Flow<WorkResult<Planet?>>
    suspend fun setPlanets(planets: List<Planet>)
    suspend fun addPlanet(planet: Planet)
    suspend fun deletePlanet(planetId: String)
}