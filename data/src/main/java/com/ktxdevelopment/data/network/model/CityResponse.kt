package com.ktxdevelopment.data.network.model

import com.google.gson.annotations.SerializedName


data class CityResponse(
    @SerializedName("cityId")
    val cityId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("peopleList")
    val peopleList: List<PersonResponse>
)