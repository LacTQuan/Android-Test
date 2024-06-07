package com.example.androidtest.di

import android.util.Log
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
            val jedis = Jedis("192.168.2.105", 6379)
            Log.d("JedisProvider", "Jedis connected")
            return jedis
        } catch (e: Exception) {
            Log.e("JedisProvider", "provideJedis: $e")
            throw e
        }
    }
}