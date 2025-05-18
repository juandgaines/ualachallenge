package com.juandgaines.challengeplaces.data

import retrofit2.Response
import retrofit2.http.GET

interface CitiesApi{
    @GET("cities.json")
    suspend fun getCities(): Response<List<CitiesDto>>

}