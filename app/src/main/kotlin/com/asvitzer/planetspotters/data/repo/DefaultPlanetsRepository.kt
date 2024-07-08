package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import kotlinx.coroutines.flow.Flow

class DefaultPlanetsRepository: PlanetsRepository {
    override fun getPlanetsFlow(): Flow<WorkResult<List<Planet>>> {
        return localDataSource.getPlanetFlow()
    }

    override fun getPlanetFlow(planetId: String): Flow<WorkResult<Planet?>> {
        return localDataSource.getPlanetsFlow()
    }

    override suspend fun refreshPlanets() {
        val planets = remoteDataSource.getPlanets()
        localDataSource.setPlanets(planets)
    }

    override suspend fun addPlanet(planet: Planet) {

    }

    override suspend fun deletePlanet(planetId: String) {
        TODO("Not yet implemented")
    }
}