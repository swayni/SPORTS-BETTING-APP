package com.swayni.sportsbettingapp.di.module

import com.swayni.sportsbettingapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("accept", "application/json")
                    .build()
                chain.proceed(request)
            })
            .retryOnConnectionFailure(true)
            .connectTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun apiCreator(client: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_API_STRING).addCallAdapterFactory(
        RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(client).build()

}