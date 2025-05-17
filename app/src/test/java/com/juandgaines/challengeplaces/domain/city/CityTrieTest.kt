package com.juandgaines.challengeplaces.domain.city

import com.google.common.truth.Truth
import org.junit.Test

class CityTrieTest {

 @Test
 fun whenInsertedCities_thenSearchCorrectPrefixCity_shouldReturnOneSuggestion() {
  val cities = listOf(
   City(1, "Alabama", "US", 0.0, 0.0),
   City(2, "Albuquerque", "US", 0.0, 0.0),
   City(3, "Anaheim", "US", 0.0, 0.0),
   City(4, "Arizona", "US", 0.0, 0.0),
   City(5, "Sydney", "AU", 0.0, 0.0)
  )

  val trie = CityTrie()
  cities.forEach { trie.insert(it) }

  val results = trie.searchByPrefix("Alb")

  Truth.assertThat(results).hasSize(1)
  Truth.assertThat(results.first().name).isEqualTo("Albuquerque")
 }

 @Test
 fun whenInsertedCities_thenSearchIncorrectPrefixCity_shouldReturnNoSuggestions() {
  val cities = listOf(
   City(1, "Alabama", "US", 0.0, 0.0),
   City(2, "Albuquerque", "US", 0.0, 0.0),
   City(3, "Anaheim", "US", 0.0, 0.0),
   City(4, "Arizona", "US", 0.0, 0.0),
   City(5, "Sydney", "AU", 0.0, 0.0)
  )

  val trie = CityTrie()
  cities.forEach { trie.insert(it) }

  val results = trie.searchByPrefix("Sid")

  Truth.assertThat(results).hasSize(0)
 }
}