package com.juandgaines.challengeplaces.data

import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val localCitiesDataSource: LocalCitiesDataSource,
    private val remoteCitiesDataSource: RemoteCitiesDataSource
):CitiesRepository {
    override suspend fun getCities(): Result<List<City>> = withContext(Dispatchers.IO) {
        remoteCitiesDataSource.getCities()
    }

    override suspend fun getCitiesByPrefix(prefix: String): List<City> = withContext(Dispatchers.IO) {
        localCitiesDataSource.getCities(prefix)
    }

    override suspend fun insertCities(cities: List<City>) = withContext(Dispatchers.IO) {
        localCitiesDataSource.upsert(cities)
    }
}