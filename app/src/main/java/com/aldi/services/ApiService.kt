package com.aldi.services

import com.aldi.ganiafoodtestrl.model.Food
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("foods")
    suspend fun getAllFoods() : Response<List<Food>>

    companion object {
        var retrofitService: ApiService? = null
        fun getInstance() : ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://3.15.16.42:7000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }

    }
}