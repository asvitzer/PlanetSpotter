package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import com.asvitzer.planetspotters.data.source.locale.LocalDataSource
import com.asvitzer.planetspotters.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultPlanetsRepository@Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
): PlanetsRepository {

    override fun getPlanetsFlow(): Flow<WorkResult<List<Planet>>> {
        return localDataSource.getPlanetsFlow()
    }

    override fun getPlanetFlow(planetId: String): Flow<WorkResult<Planet?>> {
        return localDataSource.getPlanetFlow(planetId)
    }

    override suspend fun refreshPlanets() {
        val planets = remoteDataSource.getPlanets()
        localDataSource.setPlanets(planets)
    }

    override suspend fun addPlanet(planet: Planet) {
        val planetWithId = remoteDataSource.addPlanet(planet)
        localDataSource.addPlanet(planetWithId)
    }

    override suspend fun deletePlanet(planetId: String) {
        remoteDataSource.deletePlanet(planetId)
        localDataSource.deletePlanet(planetId)
    }
}