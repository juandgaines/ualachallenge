package com.juandgaines.challengeplaces.presentation

import com.juandgaines.challengeplaces.domain.city.City

data class SearchState(
    val suggestions: List<City> = emptyList(),
    val isLoading: Boolean = true,
    val currentSelectedCity: City? = null,
    val isFavoriteFilter: Boolean = false,
)