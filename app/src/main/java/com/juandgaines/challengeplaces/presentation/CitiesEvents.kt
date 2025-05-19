package com.juandgaines.challengeplaces.presentation

import com.juandgaines.challengeplaces.domain.city.City

interface CitiesEvents {
    data class CitySelected(val city: City) : CitiesEvents
    data object NavigateBack : CitiesEvents
}