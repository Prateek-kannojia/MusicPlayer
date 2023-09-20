package com.example.musicplayer.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    val baseurl = "https://cms.samespace.com/"
    @Singleton
    @Provides
    fun retrofit():Retrofit{
        return Retrofit.Builder().baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun musicApi(retrofit: Retrofit):MusicApiService{
        return retrofit.create(MusicApiService::class.java)
    }
}