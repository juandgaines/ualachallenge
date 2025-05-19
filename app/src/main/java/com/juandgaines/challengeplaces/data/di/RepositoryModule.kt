package com.juandgaines.challengeplaces.data.di

import com.juandgaines.challengeplaces.data.CitiesRepositoryImpl
import com.juandgaines.challengeplaces.domain.city.LocalCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.CitiesRepository
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        localCitiesDataSource: LocalCitiesDataSource,
        remoteCitiesDataSource: RemoteCitiesDataSource
    ): CitiesRepository {
        return CitiesRepositoryImpl(
            localCitiesDataSource = localCitiesDataSource,
            remoteCitiesDataSource = remoteCitiesDataSource
        )
    }
}