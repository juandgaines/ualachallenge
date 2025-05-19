package com.juandgaines.challengeplaces.data

import com.google.common.truth.Truth
import com.juandgaines.challengeplaces.utils.FakeCitiesApi
import com.juandgaines.challengeplaces.utils.providerCities
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteCitiesDataSourceDefaultTest{

 private lateinit var remoteCitiesDataSourceDefault: RemoteCitiesDataSourceDefault
 private lateinit var remoteFakeCitiesApi: FakeCitiesApi

 @Before
 fun setUp() {
  remoteFakeCitiesApi = FakeCitiesApi()
  remoteCitiesDataSourceDefault = RemoteCitiesDataSourceDefault(remoteFakeCitiesApi)
 }

 @Test
 fun `getCities returns success when response is successful`() = runTest {

  remoteFakeCitiesApi.shouldFail = false
  val result = remoteCitiesDataSourceDefault.getCities()

  Truth.assertThat(result.isSuccess).isTrue()
  Truth.assertThat(result.getOrNull()).hasSize(providerCities.size)
 }

 @Test
 fun `getCities returns failure when response is unsuccessful`() = runTest {

  remoteFakeCitiesApi.shouldFail =true

  val result = remoteCitiesDataSourceDefault.getCities()

  Truth.assertThat(result.isFailure).isTrue()
 }

}