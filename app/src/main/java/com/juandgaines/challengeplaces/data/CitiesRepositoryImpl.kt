package com.juandgaines.challengeplaces.data

import com.juandgaines.challengeplaces.domain.city.AppDispatchers
import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val localCitiesDataSource: LocalCitiesDataSource,
    private val remoteCitiesDataSource: RemoteCitiesDataSource,
    private val appDispatchers: AppDispatchers
):CitiesRepository {
    override suspend fun getCities(): Result<List<City>> = withContext(appDispatchers.io) {
        remoteCitiesDataSource.getCities()
    }

    override suspend fun areCitiesInserted(): Boolean = withContext(appDispatchers.io) {
        localCitiesDataSource.areCitiesInserted()
    }

    override suspend fun getCitiesByPrefix(prefix: String): List<City> = withContext(appDispatchers.io) {
        localCitiesDataSource.getCities(prefix)
    }

    override suspend fun getCitiesByPrefixAndFavorites(prefix: String): List<City>  = withContext(appDispatchers.io) {
        localCitiesDataSource.getCitiesFavorites(prefix)
    }

    override suspend fun insertCities(cities: List<City>) = withContext(appDispatchers.io) {
        localCitiesDataSource.upsert(cities)
    }

    override suspend fun markAsFavorite(cityId: Int) = withContext(appDispatchers.io){
        localCitiesDataSource.markAsFavorite(cityId)
    }
}