package com.juandgaines.challengeplaces.data

import com.juandgaines.challengeplaces.data.database.PlacesDao
import com.juandgaines.challengeplaces.data.database.toCity
import com.juandgaines.challengeplaces.data.database.toPlacesEntity
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import javax.inject.Inject

class LocalCitiesDataSourceDefault @Inject constructor(
    private val dao: PlacesDao
) : LocalCitiesDataSource{

    override suspend fun getCities(query: String): List<City> {
        return dao.getPlacesByNamePrefix(query).map {
            it.toCity()
        }
    }

    override suspend fun upsert(cities: List<City>) {
        dao.upsertPlaces(cities.map {
            it.toPlacesEntity()
        })
    }

}