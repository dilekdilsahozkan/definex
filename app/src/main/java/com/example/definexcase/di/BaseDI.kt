package com.example.definexcase.di

import android.content.Context
import com.example.definexcase.BuildConfig
import com.example.definexcase.base.CacheInterceptor
import com.example.definexcase.base.Constants
import com.example.definexcase.data.api.DefineXService
import com.example.definexcase.data.repository.DefineXRepository
import com.example.definexcase.data.repository.DefineXRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseDI {
    private val CACHE_SIZE = 5 * 1024 * 1024L // 5 MB

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache) = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor(cache))
            .addInterceptor(loggingInterceptor)
            .build()
    } else{
        OkHttpClient
            .Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor(cache))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(DefineXService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(service: DefineXService): DefineXRepository = DefineXRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

}