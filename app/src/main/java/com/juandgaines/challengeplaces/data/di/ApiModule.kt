package com.juandgaines.challengeplaces.data.di

import com.juandgaines.challengeplaces.data.CitiesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

     @Provides
     fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
         return retrofit.create(CitiesApi::class.java)
     }
}