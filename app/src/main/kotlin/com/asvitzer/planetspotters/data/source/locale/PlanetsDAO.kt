package com.asvitzer.planetspotters.data.source.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the planets table.
 */
@Dao
interface PlanetsDao {

    /**
     * Observes list of planets.
     *
     * @return all planets.
     */
    @Query("SELECT * FROM Planets")
    fun observePlanets(): Flow<List<PlanetEntity>>

    /**
     * Observes a single planet.
     *
     * @param planetId the planet id.
     * @return the planet with planetId.
     */
    @Query("SELECT * FROM Planets WHERE planetId = :planetId")
    fun observePlanetById(planetId: String): Flow<PlanetEntity?>

    /**
     * Insert a planet in the database. If the planet already exists, replace it.
     *
     * @param planet the planet to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: PlanetEntity)

    /**
     * Delete a planet by id.
     *
     * @return the number of planets deleted. This should always be 1.
     */
    @Query("DELETE FROM Planets WHERE planetId = :planetId")
    suspend fun deletePlanetById(planetId: String): Int

    /**
     * Delete all planets.
     */
    @Query("DELETE FROM Planets")
    suspend fun deletePlanets()

    @Transaction
    suspend fun setPlanets(planets: List<PlanetEntity>) {
        deletePlanets()
        planets.forEach { insertPlanet(it) }
    }
}
