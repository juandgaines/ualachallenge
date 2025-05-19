package com.juandgaines.challengeplaces.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("places")
data class PlacesEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
)