package com.asvitzer.planetspotters.data.source.locale

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of a data source as a db.
 */
class RoomLocalDataSource internal constructor(
    private val planetsDao: PlanetsDao
) : LocalDataSource {
    override fun getPlanetsFlow(): Flow<WorkResult<List<Planet>>> {
        return planetsDao.observePlanets().map {
            WorkResult.Success(it.map { planetEntity -> planetEntity.toPlanet() })
        }
    }

    override fun getPlanetFlow(planetId: String): Flow<WorkResult<Planet?>> {
        return planetsDao.observePlanetById(planetId).map {
            WorkResult.Success(it?.toPlanet())
        }
    }

    override suspend fun setPlanets(planets: List<Planet>) {
        planetsDao.setPlanets(planets.map { it.toPlanetEntity() })
    }

    override suspend fun addPlanet(planet: Planet) {
        planetsDao.insertPlanet(planet.toPlanetEntity())
    }

    override suspend fun deletePlanet(planetId: String) {
        planetsDao.deletePlanetById(planetId)
    }
}
