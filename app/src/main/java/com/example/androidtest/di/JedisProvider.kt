package com.example.androidtest.di

import android.util.Log
import com.example.androidtest.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import redis.clients.jedis.Jedis
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class JedisProvider {

    @Provides
    @Singleton
    fun provideJedis(): Jedis {
        try {
            val jedis = Jedis(BuildConfig.REDIS_HOST, BuildConfig.REDIS_PORT.toInt())
            Log.d("JedisProvider", "Jedis connected")
            return jedis
        } catch (e: Exception) {
            Log.e("JedisProvider", "provideJedis: $e")
            throw e
        }
    }
}