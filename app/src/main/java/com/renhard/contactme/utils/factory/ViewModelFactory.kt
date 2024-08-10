package com.renhard.contactme.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renhard.contactme.module.main.viewmodel.ContactListViewModel
import com.renhard.layarkaca.di.Injection
import com.renhard.layarkaca.repository.remote.ApiDataSource

class ViewModelFactory private constructor(private val apiDataSource: ApiDataSource) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRemoteData()).apply { instance = this }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ContactListViewModel::class.java) -> {
                ContactListViewModel(apiDataSource) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}