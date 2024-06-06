package com.example.androidtest.di

import com.example.androidtest.BuildConfig
import com.example.androidtest.model.SearchApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import redis.clients.jedis.Jedis
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SearchResultApiProvider {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("X-API-KEY", BuildConfig.API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchApiService(
        retrofit: Retrofit
    ): SearchApi.Service {
        return retrofit.create(SearchApi.Service::class.java)
    }

    @Provides
    @Singleton
    fun provideJedis(): Jedis {
        return Jedis(BuildConfig.REDIS_HOST, 6379)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}