package com.juandgaines.challengeplaces.domain.city

interface LocalCitiesDataSource {
    suspend fun getCities( query:String): List<City>
    suspend fun upsert(cities: List<City>)
}