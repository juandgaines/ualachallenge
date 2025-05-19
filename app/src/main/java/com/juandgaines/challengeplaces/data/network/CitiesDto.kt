package com.juandgaines.challengeplaces.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitiesDto(
    @SerialName("_id") val id: Int,
    val name: String,
    val country: String,
    val coord: CoordDto
)

@Serializable
data class CoordDto(
    val lon: Double,
    val lat: Double
)