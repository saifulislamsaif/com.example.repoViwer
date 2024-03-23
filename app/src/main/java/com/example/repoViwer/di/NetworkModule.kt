package com.example.repoViwer.di

import com.example.repoViwer.network.ApiService
import com.example.repoViwer.utills.ConstantKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(
        httpLoggingInterceptor: HttpLoggingInterceptor

    ): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .readTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(ConstantKeys.BASE_URL)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun printApiReqResponse(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message: String? ->
//            if (BuildConfig.DEBUG)
            println(message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun providesUserAPI(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}