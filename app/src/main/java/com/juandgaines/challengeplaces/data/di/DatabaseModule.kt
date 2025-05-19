package com.juandgaines.challengeplaces.data.di

import android.content.Context
import androidx.room.Room
import com.juandgaines.challengeplaces.data.database.PlacesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): PlacesDatabase {
        return Room.databaseBuilder(
            context,
            PlacesDatabase::class.java,
            "places.db"
        ).build()
    }
}