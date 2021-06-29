package com.aldi.services

class ApiConfig constructor(private val retrofitService: ApiService) {

    suspend fun getFoods() = retrofitService.getAllFoods()

}