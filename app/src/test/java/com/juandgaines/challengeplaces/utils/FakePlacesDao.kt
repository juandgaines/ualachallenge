package com.juandgaines.challengeplaces.utils

import com.juandgaines.challengeplaces.data.database.PlacesDao
import com.juandgaines.challengeplaces.data.database.PlacesEntity

class FakePlacesDao : PlacesDao {

    private val cities = mutableListOf<PlacesEntity>()

    override suspend fun getPlacesByNamePrefix(prefix: String): List<PlacesEntity> {
        return cities.filter { it.name.startsWith(prefix, ignoreCase = true) }
            .sortedBy { it.name }
    }

    override suspend fun areCitiesInserted(): List<PlacesEntity> {
        return if (cities.isEmpty()) emptyList() else listOf(cities.first())
    }

    override suspend fun getPlacesByNamePrefixAndFavorites(prefix: String): List<PlacesEntity> {
        return cities.filter { it.isFavorite && it.name.startsWith(prefix, ignoreCase = true) }
            .sortedBy { it.name }
    }

    override suspend fun upsertPlaces(places: List<PlacesEntity>) {
        for (place in places) {
            val index = cities.indexOfFirst { it.id == place.id }
            if (index != -1) {
                cities[index] = place
            } else {
                cities.add(place)
            }
        }
    }

    override suspend fun upsertPlace(place: PlacesEntity) {
        upsertPlaces(listOf(place))
    }

    override suspend fun getPlaceById(id: Int): PlacesEntity? {
        return cities.find { it.id == id }
    }

    fun clear() {
        cities.clear()
    }
}
