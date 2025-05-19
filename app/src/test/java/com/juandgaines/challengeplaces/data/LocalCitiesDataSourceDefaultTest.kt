package com.juandgaines.challengeplaces.data

import com.google.common.truth.Truth
import com.juandgaines.challengeplaces.data.database.toPlacesEntity
import com.juandgaines.challengeplaces.utils.FakePlacesDao
import com.juandgaines.challengeplaces.utils.providerCities
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocalCitiesDataSourceDefaultTest{
 private lateinit var fakeDao: FakePlacesDao
 private lateinit var localCitiesDataSource: LocalCitiesDataSourceDefault

 @Before
 fun setUp() {
  fakeDao = FakePlacesDao()
  localCitiesDataSource = LocalCitiesDataSourceDefault(fakeDao)
 }

 @Test
 fun `upsert inserts cities into the database`() = runTest {
  val cities = providerCities
  fakeDao.clear()
  localCitiesDataSource.upsert(cities)

  val result = localCitiesDataSource.getCities("")
  Truth.assertThat(result).hasSize(cities.size)
 }

 @Test
 fun `getCities returns expected cities by prefix`() = runTest {
  reInsertCities()
  val result = localCitiesDataSource.getCities("Al")

  Truth.assertThat(result).hasSize(6)
  Truth.assertThat(result.map { it.name }).containsExactly("Alabama", "Albany","Albuquerque","Alexandria","Allen","Allentown")
 }

 @Test
 fun `areCitiesInserted returns true when cities are present`() = runTest {
  reInsertCities()
  val inserted = localCitiesDataSource.areCitiesInserted()

  Truth.assertThat(inserted).isTrue()
 }

 @Test
 fun `getCitiesFavorites returns only favorite cities by prefix`() = runTest {
  reInsertCities()
  val city = providerCities[0]
  fakeDao.upsertPlace(city.copy(isFavorite = true).toPlacesEntity())

  val result = localCitiesDataSource.getCitiesFavorites(city.name)

  Truth.assertThat(result).hasSize(1)
  Truth.assertThat(result[0].name).isEqualTo(city.name)
 }

 @Test
 fun `markAsFavorite toggles the favorite value`() = runTest {
  reInsertCities()
  val city = providerCities[0]

  val before = fakeDao.getPlaceById(city.id)
  Truth.assertThat(before?.isFavorite).isFalse()

  localCitiesDataSource.markAsFavorite(city.id)

  val after = fakeDao.getPlaceById(city.id)
  Truth.assertThat(after?.isFavorite).isTrue()
 }
 private suspend fun reInsertCities() {
  fakeDao.clear()
  fakeDao.upsertPlaces(providerCities.map { it.toPlacesEntity() })
 }
}