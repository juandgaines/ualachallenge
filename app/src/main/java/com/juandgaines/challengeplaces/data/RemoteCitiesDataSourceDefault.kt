package com.juandgaines.challengeplaces.data

import com.juandgaines.challengeplaces.data.network.CitiesApi
import com.juandgaines.challengeplaces.data.network.toDomain
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.City
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteCitiesDataSourceDefault @Inject constructor(
    private val api: CitiesApi
) : RemoteCitiesDataSource{
    override suspend fun getCities(): Result<List<City>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCities()
            if (response.isSuccessful) {
                val citiesDto = response.body() ?: emptyList()
                Result.success(citiesDto.map { it.toDomain() })
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        }
        catch (e:Exception){
            if(e is CancellationException)
                throw CancellationException()
           Result.failure(e)
        }
    }
}