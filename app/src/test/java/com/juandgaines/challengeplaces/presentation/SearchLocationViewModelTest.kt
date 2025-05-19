package com.juandgaines.challengeplaces.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.juandgaines.challengeplaces.data.CitiesRepositoryImpl
import com.juandgaines.challengeplaces.data.LocalCitiesDataSourceDefault
import com.juandgaines.challengeplaces.data.RemoteCitiesDataSourceDefault
import com.juandgaines.challengeplaces.data.database.toPlacesEntity
import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import com.juandgaines.challengeplaces.utils.AppTestDispatchers
import com.juandgaines.challengeplaces.utils.FakeCitiesApi
import com.juandgaines.challengeplaces.utils.FakePlacesDao
import com.juandgaines.challengeplaces.utils.providerCities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchLocationViewModelTest{
 private lateinit var searchLocationViewModel: SearchLocationViewModel
 private lateinit var testAppTestDispatchers: AppTestDispatchers
 private lateinit var repository: CitiesRepository
 private lateinit var remoteCitiesDataSource: RemoteCitiesDataSource
 private lateinit var localCitiesDataSource: LocalCitiesDataSource
 private lateinit var fakeDao: FakePlacesDao
 private lateinit var fakeCitiesApi: FakeCitiesApi

 @Before
 fun setup() {

  fakeDao = FakePlacesDao()
  fakeCitiesApi = FakeCitiesApi()
  testAppTestDispatchers = AppTestDispatchers()
  Dispatchers.setMain(testAppTestDispatchers.testDispatcher)
  localCitiesDataSource = LocalCitiesDataSourceDefault(fakeDao)
  remoteCitiesDataSource = RemoteCitiesDataSourceDefault(fakeCitiesApi)
  repository = CitiesRepositoryImpl(localCitiesDataSource, remoteCitiesDataSource, testAppTestDispatchers)
  searchLocationViewModel = SearchLocationViewModel(repository,testAppTestDispatchers)

  runTest {
   reInsertCities()
  }
 }

 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
 fun givenNoQuery_whenInitialized_thenVerifyEmptyList() = runTest{
  reInsertCities()
  searchLocationViewModel.state.test {
    searchLocationViewModel.onAction(CitiesIntent.OnQueryChange(""))
    val state = awaitItem()
    Truth.assertThat(state.suggestions).isEmpty()
  }
 }
 @Test
 fun givenQuery_whenTyped_thenVerifySizeOfList () = runTest {
  searchLocationViewModel.state.test {
   reInsertCities()
   awaitItem()
   searchLocationViewModel.onAction(CitiesIntent.OnQueryChange("Al"))

   val filtered = awaitItem()
   Truth.assertThat(filtered.suggestions).hasSize(6)

   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun givenQuery_whenCLickedSelectionPlate_thenVerifyPlaceIsSelected () = runTest {

  searchLocationViewModel.state.test {
   reInsertCities()
   awaitItem()
    searchLocationViewModel.onAction(CitiesIntent.OnQueryChange("Al"))
    val filtered = awaitItem()
   Truth.assertThat(filtered.suggestions).hasSize(6)
    searchLocationViewModel.onAction(CitiesIntent.OnCityClick(filtered.suggestions[0]))
    val selected = awaitItem()
    Truth.assertThat(selected.currentSelectedCity).isEqualTo(filtered.suggestions[0])
   cancelAndIgnoreRemainingEvents()
  }
 }

 @Test
 fun givenCheckFavorites_whenClicked_thenVerifyJustFavorites() = runTest {
  searchLocationViewModel.state.test {
   reInsertCities()
   awaitItem()
   searchLocationViewModel.onAction(CitiesIntent.OnQueryChange("Al"))
    val filtered = awaitItem()
    Truth.assertThat(filtered.suggestions).hasSize(6)
   searchLocationViewModel.onAction(CitiesIntent.ToggleFavorite(filtered.suggestions[0]))
    val selected = awaitItem()
   searchLocationViewModel.onAction(CitiesIntent.OnShowFavorites(true))
    val filteredFavorites = awaitItem()
    Truth.assertThat(filteredFavorites.suggestions).hasSize(1)
  }
 }

 @Test
 fun givenQuery_whenQueryCleared_thenVerifyEmptyList() = runTest {

  searchLocationViewModel.state.test {
   reInsertCities()
   awaitItem()
   searchLocationViewModel.onAction(CitiesIntent.OnQueryChange("Al"))
   val filtered = awaitItem()
   Truth.assertThat(filtered.suggestions).hasSize(6)
   searchLocationViewModel.onAction(CitiesIntent.OnClearClick)
   val state = awaitItem()
   Truth.assertThat(state.suggestions).isEmpty()
  }
 }

 private suspend fun reInsertCities() {
  fakeDao.clear()
  fakeDao.upsertPlaces(providerCities.map { it.toPlacesEntity() })
 }

}