package com.juandgaines.challengeplaces.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface PlacesDao{
    @Query("SELECT * FROM places WHERE name LIKE :prefix || '%' ORDER BY name ASC")
    suspend fun getPlacesByNamePrefix(prefix: String): List<PlacesEntity>

    @Query("SELECT * FROM places LIMIT 1")
    suspend fun areCitiesInserted(): List<PlacesEntity>

    @Query("SELECT * FROM places WHERE name LIKE :prefix || '%' AND isFavorite = 1 ORDER BY name ASC")
    suspend fun getPlacesByNamePrefixAndFavorites(prefix: String): List<PlacesEntity>

    @Transaction
    @Upsert
    suspend fun upsertPlaces(places: List<PlacesEntity>)

    @Upsert
    suspend fun upsertPlace(place: PlacesEntity)

    @Query ("SELECT * FROM places WHERE id = :id")
    suspend fun getPlaceById(id: Int): PlacesEntity?



}