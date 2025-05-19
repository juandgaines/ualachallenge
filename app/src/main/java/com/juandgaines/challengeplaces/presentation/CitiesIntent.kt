package com.juandgaines.challengeplaces.presentation

import com.juandgaines.challengeplaces.domain.city.City

interface CitiesIntent {
    data class OnQueryChange(val query: String) : CitiesIntent
    data class OnCityClick(val city: City?) : CitiesIntent
    data class OnShowFavorites(val isFavorites: Boolean) : CitiesIntent
    data class ToggleFavorite(val city: City) : CitiesIntent
    data object OnClearClick : CitiesIntent
    data object NavigateBack : CitiesIntent
}