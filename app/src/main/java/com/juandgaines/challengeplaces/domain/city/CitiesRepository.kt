package com.juandgaines.challengeplaces.domain.city

interface CitiesRepository {

    suspend fun getCities(): Result<List<City>>

    suspend fun getCitiesByPrefix(prefix: String): List<City>

    suspend fun insertCities(cities: List<City>)
}