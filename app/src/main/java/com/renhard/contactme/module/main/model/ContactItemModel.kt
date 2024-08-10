package com.renhard.contactme.module.main.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContactItemModel(
    @field:SerializedName("id")
    val id: Long,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("username")
    val userName: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("address")
    val address: AddressModel,

    var thumbnail: String
)

data class AddressModel(
    @field:SerializedName("street")
    val street: String,

    @field:SerializedName("suite")
    val suite: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("zipcode")
    val zipcode: String,

    @field:SerializedName("geo")
    val geo: CoordinateModel
): Serializable

data class CoordinateModel(
    @field:SerializedName("lat")
    val lat: String,

    @field:SerializedName("lng")
    val lng: String
): Serializable