package com.juandgaines.challengeplaces.data.database

import com.juandgaines.challengeplaces.domain.city.City


fun PlacesEntity.toCity(): City {
    return City(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon,
        isFavorite = isFavorite
    )
}

fun City.toPlacesEntity(): PlacesEntity {
    return PlacesEntity(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon,
        isFavorite = isFavorite
    )
}