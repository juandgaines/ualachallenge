package com.juandgaines.challengeplaces.data

import retrofit2.http.GET

interface CitiesApi{
    @GET
    suspend fun getCities(): List<CitiesDto>

}