package com.example.androidtest.di

import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GsonProvider {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}