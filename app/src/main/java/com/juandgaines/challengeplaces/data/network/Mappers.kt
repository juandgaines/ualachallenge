package com.juandgaines.challengeplaces.data.network

import com.juandgaines.challengeplaces.domain.city.City

fun CitiesDto.toDomain(): City {
    return City(
        id = id,
        name = name,
        country = country,
        lat = coord.lat,
        lon = coord.lon
    )
}