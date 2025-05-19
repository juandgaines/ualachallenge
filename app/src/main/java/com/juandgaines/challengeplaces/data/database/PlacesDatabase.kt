package com.juandgaines.challengeplaces.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PlacesEntity::class,
    ], version = 1)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}