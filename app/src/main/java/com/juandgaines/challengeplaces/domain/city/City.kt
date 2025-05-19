package com.juandgaines.challengeplaces.domain.city

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val isFavorite: Boolean = false,
)