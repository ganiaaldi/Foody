package com.aldi.ganiafoodtestrl.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aldi.ganiafoodtestrl.model.Food
import com.aldi.services.ApiConfig
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val mainRepository: ApiConfig) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val foodList = MutableLiveData<List<Food>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllFood() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getFoods()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    foodList.postValue(response.body())
                    loading.postValue(false)
                    Log.d("Response","${response.body()}")
                } else {
                    onError("Error : ${response.message()} ")
                    Log.d("Response", response.message())
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}