package com.example.desafiotqi.network

import com.example.desafiotqi.BuildConfig
import com.example.desafiotqi.MyApplication
import com.example.desafiotqi.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private fun getRetrofit(): Retrofit {

    // log
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
    } else {
        logging.level = HttpLoggingInterceptor.Level.BASIC
    }

    // http client
    val httpClient = OkHttpClient.Builder()

    // timeouts
    val context = MyApplication.context
    val timeout = context.getString(R.string.api_timeout_seconds).toLong()
    httpClient.connectTimeout(timeout, TimeUnit.SECONDS)
    httpClient.readTimeout(timeout, TimeUnit.SECONDS)
    httpClient.writeTimeout(timeout, TimeUnit.SECONDS)

    // interceptors
    httpClient.addInterceptor(logging)

    return Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .baseUrl(context.getString(R.string.base_url)).build()
}

object ApiServices {
    val myApi: MyApi by lazy { getRetrofit().create(MyApi::class.java) }
}
