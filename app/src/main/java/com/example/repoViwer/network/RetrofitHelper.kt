package com.example.repoViwer.network

import com.example.repoViwer.utills.ConstantKeys
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    fun getInstance(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(printApiReqResponse())

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(ConstantKeys.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getInstance(baseUrl: String = ConstantKeys.BASE_URL2): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(ConstantKeys.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(printApiReqResponse())
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build()
                chain.proceed(request)
            })

        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun printApiReqResponse(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message: String? ->
//            if (BuildConfig.DEBUG)
            println(message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }
}