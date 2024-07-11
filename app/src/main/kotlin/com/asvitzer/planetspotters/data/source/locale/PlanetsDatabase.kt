package com.asvitzer.planetspotters.data.source.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Database(entities = [PlanetEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlanetsDatabase : RoomDatabase() {

    abstract fun planetsDao(): PlanetsDao
}

object Converters {
    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time
}