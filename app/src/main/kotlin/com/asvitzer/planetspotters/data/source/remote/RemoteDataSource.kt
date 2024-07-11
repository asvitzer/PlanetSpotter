package com.asvitzer.planetspotters.data.source.remote

import com.asvitzer.planetspotters.data.model.Planet

interface RemoteDataSource {
    suspend fun getPlanets(): List<Planet>
    suspend fun addPlanet(planet: Planet): Planet
    suspend fun deletePlanet(planetId: String)
}