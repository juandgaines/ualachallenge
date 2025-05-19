package com.juandgaines.challengeplaces.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule{
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

         val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}