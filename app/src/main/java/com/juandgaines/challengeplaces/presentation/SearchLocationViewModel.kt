package com.juandgaines.challengeplaces.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.domain.city.CityTrie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
): ViewModel() {


    private val _eventsChannel = Channel<CitiesEvents>(Channel.BUFFERED)
    val events = _eventsChannel

    private val _query = MutableStateFlow("")
    private val _selectedCity = MutableStateFlow<City?>(null)
    val query = _query

    private val _trie = CityTrie()

    val  state = _query
        .onStart {
            val result = citiesRepository.getCities()

            if (result.isSuccess) {
                val cities = result.getOrNull()
                if (cities != null) {
                    citiesRepository.insertCities(cities)
                }
            }
        }
        .flowOn(Dispatchers.Default)
        .map { query->
            val cities = if (query.isEmpty()) emptyList() else citiesRepository.getCitiesByPrefix(query)

            if (_trie.shouldRebuildFor(query)) {
                _trie.clear()
                cities.forEach { city ->
                    _trie.insert(city)
                }
                _trie.setLastPrefix(query)
            }

            val suggestions = if (query.isEmpty()) {
                emptyList<City>()
            } else {
                _trie.searchByPrefix(query)
            }

            SearchState(
                currentQuery = query,
                suggestions = suggestions,
                isLoading = false,
            )

        }.combine(
            _selectedCity
        ) { state, selectedCity ->
            state.copy(
                currentSelectedCity = selectedCity
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchState()
        )

    fun onAction(intent:CitiesIntent){
        viewModelScope.launch {
            when(intent){
                is CitiesIntent.OnQueryChange -> {
                    _query.update {
                        intent.query
                    }
                }
                CitiesIntent.OnClearClick -> {
                    _query.update {
                        ""
                    }

                }
                is CitiesIntent.OnCityClick -> {
                    _selectedCity.update {
                        intent.city
                    }

                }
                CitiesIntent.NavigateBack -> {
                    _eventsChannel.send(
                        CitiesEvents.NavigateBack
                    )
                }
            }
        }

    }
}