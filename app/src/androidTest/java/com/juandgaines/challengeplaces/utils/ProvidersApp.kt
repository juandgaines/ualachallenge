package com.juandgaines.challengeplaces.utils

import com.juandgaines.challengeplaces.data.network.CitiesDto
import com.juandgaines.challengeplaces.data.network.CoordDto
import com.juandgaines.challengeplaces.data.network.toDomain



val providerCitiesDto = listOf(
    CitiesDto(1, "Alabama", "US", CoordDto(lon = -86.7911, lat = 32.8067)),
    CitiesDto(2, "Albuquerque", "US", CoordDto(lon = -106.6504, lat = 35.0844)),
    CitiesDto(3, "Anaheim", "US", CoordDto(lon = -117.9145, lat = 33.8353)),
    CitiesDto(4, "Arizona", "US", CoordDto(lon = -111.0937, lat = 34.0489)),
    CitiesDto(5, "Sydney", "AU", CoordDto(lon = 151.2093, lat = -33.8688)),

    CitiesDto(6, "Albany", "US", CoordDto(lon = -73.7562, lat = 42.6526)),
    CitiesDto(7, "Alexandria", "US", CoordDto(lon = -77.0469, lat = 38.8048)),
    CitiesDto(8, "Allentown", "US", CoordDto(lon = -75.4902, lat = 40.6084)),
    CitiesDto(9, "Allen", "US", CoordDto(lon = -96.6690, lat = 33.1032)),
    CitiesDto(10, "Auckland", "NZ", CoordDto(lon = 174.7633, lat = -36.8485)),

    CitiesDto(11, "Boston", "US", CoordDto(lon = -71.0589, lat = 42.3601)),
    CitiesDto(12, "Baltimore", "US", CoordDto(lon = -76.6122, lat = 39.2904)),
    CitiesDto(13, "Birmingham", "UK", CoordDto(lon = -1.8998, lat = 52.4862)),
    CitiesDto(14, "Barcelona", "ES", CoordDto(lon = 2.1734, lat = 41.3851)),
    CitiesDto(15, "Brisbane", "AU", CoordDto(lon = 153.0251, lat = -27.4698)),

    CitiesDto(16, "Chicago", "US", CoordDto(lon = -87.6298, lat = 41.8781)),
    CitiesDto(17, "Columbus", "US", CoordDto(lon = -82.9988, lat = 39.9612)),
    CitiesDto(18, "Cleveland", "US", CoordDto(lon = -81.6944, lat = 41.4993)),
    CitiesDto(19, "Copenhagen", "DK", CoordDto(lon = 12.5683, lat = 55.6761)),
    CitiesDto(20, "Cairo", "EG", CoordDto(lon = 31.2357, lat = 30.0444)),

    CitiesDto(21, "Dallas", "US", CoordDto(lon = -96.7970, lat = 32.7767)),
    CitiesDto(22, "Denver", "US", CoordDto(lon = -104.9903, lat = 39.7392)),
    CitiesDto(23, "Detroit", "US", CoordDto(lon = -83.0458, lat = 42.3314)),
    CitiesDto(24, "Dubai", "AE", CoordDto(lon = 55.2708, lat = 25.2048)),
    CitiesDto(25, "Dublin", "IE", CoordDto(lon = -6.2603, lat = 53.3498)),

    CitiesDto(26, "Syracuse", "US", CoordDto(lon = -76.1474, lat = 43.0481)),
    CitiesDto(27, "San Francisco", "US", CoordDto(lon = -122.4194, lat = 37.7749)),
    CitiesDto(28, "San Diego", "US", CoordDto(lon = -117.1611, lat = 32.7157)),
    CitiesDto(29, "São Paulo", "BR", CoordDto(lon = -46.6333, lat = -23.5505)),
    CitiesDto(30, "Stockholm", "SE", CoordDto(lon = 18.0686, lat = 59.3293))
)

val providerCities = providerCitiesDto.map {
    it.toDomain()
}
