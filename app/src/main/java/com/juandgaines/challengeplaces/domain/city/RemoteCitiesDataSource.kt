package com.juandgaines.challengeplaces.domain.city

interface RemoteCitiesDataSource {
    suspend fun getCities(): Result<List<City>>
}