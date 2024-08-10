package com.renhard.layarkaca.di

import android.content.Context
import com.renhard.layarkaca.repository.remote.ApiConfig
import com.renhard.layarkaca.repository.remote.ApiDataSource

object Injection {
    fun provideRemoteData(): ApiDataSource {

        val apiService = ApiConfig.getApiService()
        val apiDataSource = ApiDataSource.getInstance(apiService)

        return apiDataSource
    }
}