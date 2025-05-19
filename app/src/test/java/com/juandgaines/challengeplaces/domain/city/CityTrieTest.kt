package com.juandgaines.challengeplaces.domain.city

import com.google.common.truth.Truth
import com.juandgaines.challengeplaces.utils.providerCities
import org.junit.Test

class CityTrieTest {

 @Test
 fun whenInsertedCities_thenSearchCorrectPrefixCity_shouldReturnOneSuggestion() {
  val cities = providerCities

  val trie = CityTrie()
  cities.forEach { trie.insert(it) }

  val results = trie.searchByPrefix("Al")

  Truth.assertThat(results).hasSize(6)
 }

 @Test
 fun whenInsertedCities_thenSearchIncorrectPrefixCity_shouldReturnNoSuggestions() {
  val cities = providerCities

  val trie = CityTrie()
  cities.forEach { trie.insert(it) }

  val results = trie.searchByPrefix("Sid")

  Truth.assertThat(results).hasSize(0)
 }
}