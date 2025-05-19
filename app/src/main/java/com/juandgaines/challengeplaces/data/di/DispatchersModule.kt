package com.juandgaines.challengeplaces.data.di

import com.juandgaines.challengeplaces.data.AppDispatchersDefault
import com.juandgaines.challengeplaces.domain.city.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Singleton
    fun providesAppDispatchers(): AppDispatchers {
        return AppDispatchersDefault()
    }
}