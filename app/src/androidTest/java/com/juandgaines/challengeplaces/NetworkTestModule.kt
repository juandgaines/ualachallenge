package com.juandgaines.challengeplaces

import com.juandgaines.challengeplaces.data.RemoteCitiesDataSourceDefault
import com.juandgaines.challengeplaces.data.di.ApiModule
import com.juandgaines.challengeplaces.data.di.NetworkModule
import com.juandgaines.challengeplaces.data.network.CitiesApi
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import com.juandgaines.challengeplaces.utils.FakeCitiesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class,ApiModule::class]
)
class NetworkTestModule {

    @Provides
    @Singleton
    fun providesRemoteDataSource(citiesApi: CitiesApi): RemoteCitiesDataSource {
        return RemoteCitiesDataSourceDefault(citiesApi)
    }

    @Singleton
    @Provides
    fun provideApi(): CitiesApi =  FakeCitiesApi()
}