package com.juandgaines.challengeplaces.utils

import android.content.Context
import androidx.room.Room
import com.juandgaines.challengeplaces.data.database.PlacesDatabase
import com.juandgaines.challengeplaces.data.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class DatabaseTestModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context,
    ): PlacesDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            PlacesDatabase::class.java,
        ).allowMainThreadQueries()
            .build()
    }
}