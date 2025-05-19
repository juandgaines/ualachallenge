package com.juandgaines.challengeplaces.data

import com.google.common.truth.Truth
import com.juandgaines.challengeplaces.data.database.toPlacesEntity
import com.juandgaines.challengeplaces.utils.AppTestDispatchers
import com.juandgaines.challengeplaces.utils.FakeCitiesApi
import com.juandgaines.challengeplaces.utils.FakePlacesDao
import com.juandgaines.challengeplaces.utils.providerCities
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class CitiesRepositoryImplTest{
 private lateinit var repository: CitiesRepositoryImpl
 private lateinit var testAppTestDispatchers: AppTestDispatchers
 private lateinit var fakeDao: FakePlacesDao
 private lateinit var fakeApi: FakeCitiesApi

 @Before
 fun setup() {
  fakeDao = FakePlacesDao()
  fakeApi = FakeCitiesApi()
  testAppTestDispatchers = AppTestDispatchers()

  val local = LocalCitiesDataSourceDefault(fakeDao)
  val remote = RemoteCitiesDataSourceDefault(fakeApi)
  repository = CitiesRepositoryImpl(local, remote, testAppTestDispatchers)
 }

 @Test
 fun `getCities returns success with remote data`() = runTest {
  reInsertCities()
  val result = repository.getCities()

  Truth.assertThat(result.isSuccess).isTrue()
  Truth.assertThat(result.getOrNull()).hasSize(providerCities.size)
 }

 @Test
 fun `areCitiesInserted returns true when data exists`() = runTest {
  reInsertCities()

  val inserted = repository.areCitiesInserted()
  Truth.assertThat(inserted).isTrue()
 }

 @Test
 fun `getCitiesByPrefix filters local data`() = runTest {
  reInsertCities()

  val result = repository.getCitiesByPrefix("Al")
  Truth.assertThat(result).hasSize(6)
 }

 @Test
 fun `getCitiesByPrefixAndFavorites filters only favorites`() = runTest {
  reInsertCities()
  val favorite = providerCities[1].copy(isFavorite = true)
  repository.insertCities(listOf(providerCities[0], favorite))

  val result = repository.getCitiesByPrefixAndFavorites("Al")
  Truth.assertThat(result).hasSize(1)
  Truth.assertThat(result.first().isFavorite).isTrue()
 }

 @Test
 fun `markAsFavorite toggles the favorite status`() = runTest {
  reInsertCities()
  val before = fakeDao.getPlaceById(1)
  Truth.assertThat(before?.isFavorite).isFalse()

  repository.markAsFavorite(1)

  val after = fakeDao.getPlaceById(1)
  Truth.assertThat(after?.isFavorite).isTrue()
 }

 private suspend fun reInsertCities() {
  fakeDao.clear()
  fakeDao.upsertPlaces(providerCities.map { it.toPlacesEntity() })
 }
}