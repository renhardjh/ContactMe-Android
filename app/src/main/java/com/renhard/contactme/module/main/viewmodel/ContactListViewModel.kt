package com.renhard.contactme.module.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.renhard.contactme.module.main.model.ContactItemModel
import com.renhard.contactme.repository.model.Resource
import com.renhard.layarkaca.repository.remote.ApiDataSource

class ContactListViewModel(private val apiDataSource: ApiDataSource): ViewModel() {
    lateinit var dataUsers: List<ContactItemModel>

    fun loadUsersContact(): LiveData<Resource<List<ContactItemModel>>> = apiDataSource.getUsers()
}