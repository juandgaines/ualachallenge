package com.juandgaines.challengeplaces.data.di

import com.juandgaines.challengeplaces.data.CitiesApi
import com.juandgaines.challengeplaces.data.RemoteCitiesDataSourceDefault
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

     @Provides
     @Singleton
     fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
         return retrofit.create(CitiesApi::class.java)
     }

    @Provides
    @Singleton
    fun providesRemoteDataSource(citiesApi: CitiesApi): RemoteCitiesDataSource {
        return RemoteCitiesDataSourceDefault(citiesApi)
    }

}