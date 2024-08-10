package com.renhard.layarkaca.repository.remote

import com.renhard.contactme.module.main.model.ContactItemModel
import com.renhard.contactme.utils.constant.Constant
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(Constant.API_USERS)
    fun getUsers(): Call<List<ContactItemModel>>
}