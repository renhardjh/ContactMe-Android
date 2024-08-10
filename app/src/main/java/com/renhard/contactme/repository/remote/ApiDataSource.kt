package com.renhard.layarkaca.repository.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.renhard.contactme.module.main.model.ContactItemModel
import com.renhard.contactme.module.main.model.ErrorResult
import com.renhard.contactme.repository.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiDataSource private constructor(private val apiService: ApiService) {
    val onFailedState = MutableLiveData<ErrorResult>()

    companion object {
        @Volatile
        private var instance: ApiDataSource? = null

        fun getInstance(apiService: ApiService): ApiDataSource =
            instance ?: synchronized(this) {
                ApiDataSource(apiService).apply { instance = this }
            }
    }

    fun getUsers(): LiveData<Resource<List<ContactItemModel>>> {
        val results = MutableLiveData<Resource<List<ContactItemModel>>>()
        val client = apiService.getUsers()
        client.enqueue(object : Callback<List<ContactItemModel>> {
            override fun onResponse(
                call: Call<List<ContactItemModel>>,
                response: Response<List<ContactItemModel>>
            ) {
                if (response.isSuccessful) {
                    results.value = Resource.success(response.body())
                } else {
                    Log.e("getUsers", "onFailure: ${response.message()}")
                    response.errorBody()?.let {
                        results.value = Resource.error(response.errorBody().toString(), null)
                    }
                }
            }

            override fun onFailure(call: Call<List<ContactItemModel>>, t: Throwable) {
                Log.e("getUsers", "onFailure: ${t.message.toString()}")
                onFailedState.postValue(ErrorResult("users", t.message.toString()))
            }
        })
        return results
    }
}