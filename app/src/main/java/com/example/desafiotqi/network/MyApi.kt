package com.example.desafiotqi.network

import com.example.desafiotqi.model.Bank
import retrofit2.Call
import retrofit2.http.GET

interface MyApi {

    @GET("list")
    fun getBanks(): Call<List<Bank>>

}
