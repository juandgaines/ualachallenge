package com.juandgaines.challengeplaces.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.domain.city.CityTrie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
): ViewModel() {


    private val _prefixSearch = MutableStateFlow("a")
    private val _state = MutableStateFlow(SearchState())
    private val _trie = CityTrie()

    val  state = _prefixSearch
        .onStart {
            val result = citiesRepository.getCities()

            if (result.isSuccess) {
                val cities = result.getOrNull()
                if (cities != null) {
                    citiesRepository.insertCities(cities)
                    _prefixSearch.value = "a"
                }
            }
        }
        .debounce(200)
        .flowOn(Dispatchers.Default)
        .onEach { prefix->
            if (prefix.isEmpty()) {
                _trie.clear()
                _state.value = _state.value.copy(
                    currentQuery = "",
                    suggestions = emptyList(),
                )
            }
            else {
                if(_trie.shouldRebuildFor(prefix)){
                    _trie.clear()
                    val cities = citiesRepository.getCitiesByPrefix(prefix)
                    cities.forEach { city ->
                        _trie.insert(city)
                    }
                    _trie.setLastPrefix(prefix)
                }
            }
        }
        .map { prefix->
            val suggestions = if(prefix.isEmpty()){
                emptyList<City>()
            }
            else _trie.searchByPrefix(prefix)

            _state.value = _state.value.copy(
                currentQuery = prefix,
                suggestions = suggestions,
            )
            _state.value

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchState()
        )
}