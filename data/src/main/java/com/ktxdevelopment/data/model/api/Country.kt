package com.ktxdevelopment.data.model.api

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ktxdevelopment.data.model.room.CityEntity

data class Country(
    @SerializedName("countryId")
    @PrimaryKey(autoGenerate = false)
    val countryId: Int,
    @SerializedName("cityList")
    val cityList: List<CityEntity>,
    @SerializedName("name")
    val name: String
)