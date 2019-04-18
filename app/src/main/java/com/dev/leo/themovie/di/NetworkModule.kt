package com.dev.leo.themovie.di

import com.dev.leo.themovie.BuildConfig
import com.dev.leo.themovie.BuildConfig.BASE_URL
import com.dev.leo.themovie.network.MovieApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECTION_TIME_OUT = 20L

@Module
class NetworkModule {
    @Provides
    fun provideNewsApi(retrofit: Retrofit) = retrofit.create(MovieApiService::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val debugLoggingInterceptor = HttpLoggingInterceptor()
        debugLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(debugLoggingInterceptor)
            .build()
    }
}