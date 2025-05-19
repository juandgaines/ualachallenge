package com.juandgaines.challengeplaces.presentation

import com.juandgaines.challengeplaces.domain.city.City

data class SearchState(
    val currentQuery: String = "",
    val suggestions: List<City> = emptyList(),
    val currentSelectedCity: City? = null,
)