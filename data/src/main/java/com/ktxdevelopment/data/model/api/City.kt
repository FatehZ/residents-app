package com.ktxdevelopment.data.model.api

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("peopleList")
    val peopleList: List<People>
)