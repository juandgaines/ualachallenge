package com.juandgaines.challengeplaces.data.di

import com.juandgaines.challengeplaces.data.LocalCitiesDataSourceDefault
import com.juandgaines.challengeplaces.data.database.PlacesDao
import com.juandgaines.challengeplaces.data.database.PlacesDatabase
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun provideDaoPlaces(
        database: PlacesDatabase
    ) = database.placesDao()

    @Provides
    @Singleton
    fun provideLocalCitiesDataSource(
        placesDao: PlacesDao
    ) : LocalCitiesDataSource =
        LocalCitiesDataSourceDefault(placesDao)

}